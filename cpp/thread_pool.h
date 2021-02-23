#pragma once
#include <cstdint>
#include <list>
#include <thread>
#include "auto_queue.h"

using std::thread;
using std::thread::id;
using std::mutex;
using std::lock_guard;

class ThreadPool
{
public:
    typedef void (*Cb)(void*);

    struct Runable{
        Runable(void* data,Cb cb):data_(data),cb_(cb)
        {

        }
        void *data_;
        Cb cb_;
        void operator()() {cb_(data_);}
    };

    static ThreadPool& Instance()
    {
        if(instance_==NULL)
        {
            instance_ = new ThreadPool();
        }
        return *instance_;
    }

    //提交任务到线程池，队列满时会阻塞等待
    void Submit(const Runable &runner)
    {
        queue_.Enqueue(runner);
    }

    //提交任务到线程池，队列满时，不等待直接返回false
    bool SubmitUnblock(const Runable &runner)
    {
        return queue_.EnqueueUnblock(runner);
    }

private:
    DISALLOW_COPY_AND_ASSIGN(ThreadPool);

    ThreadPool():
        queue_(queue_captity_)
    {
        for(int i=0;i<core_pool_size_;++i)
        {
            threads_.push_back(thread(ThreadPool::run,this));
        }
    }

    ~ThreadPool()
    {
        for(thread &item: threads_)
        {
            item.detach();
        }
    }

    static void run(ThreadPool *thread_pool)
    {
        thread_pool->worker();
    }

    void worker()
    {
        uint64_t start_idle_time=time(NULL);
        while(true)
        {
            if(allow_core_thread_timeout_ ||threads_.size()>core_pool_size_))
            {
                if(time(NULL)-start_idle_time>keep_alive_time_)
                {
                    //线程空闲时间过长,线程退出。
                    id thread_id = this_thread::get_id();
                    for(auto it=threads_.begin();it!=threads_.end();++it)
                    {
                        if(it->get_id() == thread_id)
                        {
                            it->detach();
                            threads_.erase(item);
                            return;
                        }
                    }
                }
            }
            
            Runable runner;
            if(queue_.DequeueUnblock(runner))
            {
                runner();
                start_idle_time = time(NULL);
            }

            //任务队列满了，并且线程数没有超过largest_pool_size_，则创建新的线程。
            lock_guard lck (mtx_);
            if(queue_.isFull() && threads_.size()<largest_pool_size_)
            {
                threads_.push_back(std::thread(ThreadPool::run,this));
            }
        }
    }

private:
    int core_pool_size_; //核心线程数
    int largest_pool_size_; //最大线程数
    bool allow_core_thread_timeout_; //核心线程是否允许超时回收
    int keep_alive_time_;  //空闲线程的存活时间
    int queue_captity_; //队列大小
    list<thread> threads_;
    AutoQueue<Runable> queue_;
    static ThreadPool* instance_;
    mutex mtx_;
};