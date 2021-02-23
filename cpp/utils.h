#ifndef UTILS_H
#define UTILS_H

#include <sstream>

namespace utils
{

/**
 *  任意变量转化成字符串的模板函数。
 *
 *  该函数将指定的变量转换成字符信息。
 *  @param  num 要转成字符串的变量。
 *  @return     变量的字符串信息。
 */
template <class T>
std::string ToString(T num)
{
    std::ostringstream  buf;
    buf << num;

    return buf.str();
}
}   // namespace utils

#endif  // UTILS_H