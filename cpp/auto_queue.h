#pragma once

#include <queue>
#include <condition_variable>
#include <mutex>
#include <atomic>

using std::queue;
using std::mutex;
using std::unique_lock;
using std::atomic;

template<typename T>
class AutoQueue
{
 public:
	AutoQueue(size_t captity)
	{
		captity_ = captity;
        size_ = 0;
	}
    
	virtual ~AutoQueue()
	{
	}

	void Enqueue(const T& item)
	{
		unique_lock<mutex> lck(m_mtx);
		cv_.wait(lck, [this] {return size_<captity_; });
		queue_.push(item);
        size_++;
        // cv_.notify_all();
        cv2_.notify_all();
	}

    bool EnqueueUnblock(const T& item)
	{
        for (int i=0; i < kSpinTimes && size_==captity_; i++)
		{
		    this_thread::yield();
		}
		if (size_==captity_)
		{
		    return false;
		}
		Enqueue(item);
		return true;
	}

    T& Dequeue()
    {
        unique_lock<mutex> lck(m_mtx);
        // cv_.wait(lck, [this] {return size_>0;});
        cv2_.wait(lck, [this] {return size_>0;});
        T& item = queue_.front();
        queue_.pop();
        size_--;
        cv_.notify_all();
        return item;
    }

	bool DequeueUnblock(T& item)
	{
		for (int i=0; i < kSpinTimes && size_==0; i++)
		{
		    this_thread::yield();
		}
		if (size_==0)
		{
		    return false;
		}
		item=Dequeue();
		return true;
	}

    bool IsFull()
    {
        return size_==captity_;
    }
    
 private:
    const int kSpinTimes = 5;
	queue<T> queue_;
	size_t captity_;
    atomic<int> size_;
	condition_variable cv_;
    condition_variable cv2_;
	mutex mtx_;
};
