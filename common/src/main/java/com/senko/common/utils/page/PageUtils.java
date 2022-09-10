package com.senko.common.utils.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

/**
 * 分页工具类
 *
 * @author senko
 * @date 2022/9/10 15:58
 */
public class PageUtils {

    /**
     * 封装分页的属性
     */
    private static final ThreadLocal<Page<?>> PAGE_HOLDER = new ThreadLocal<>();

    /**
     * 为当期的线程设置PAGE
     */
    public static Page<?> setPage(Page<?> curPage) {
        PAGE_HOLDER.set(curPage);
        return curPage;
    }

    /**
     * 删除当前请求的Page
     */
    public static void removePage() {
        PAGE_HOLDER.remove();
    }

    /**
     * 获取Page
     */
    public static Page<?> getPage() {
        Page<?> page = PAGE_HOLDER.get();
        if (Objects.isNull(page)) {
            // 如果不存在，则设置
            page = setPage(new Page<>());
        }
        return page;
    }

    /**
     * 调用Page对象的getCurrent()方法，
     * 这里得到的current是单纯的从数字1开始，一个一个加一，也就是翻一页。
     * 用在Mapper.xml或者是selectPage()这些方法里，它会自动计算
     * Limit #{计算后的current}, #{size}作为sql的后缀。
     *
     * @return  当前页页码
     */
    public static Long getCurrent() {
        return getPage().getCurrent();
    }

    /**
     * 获取分页偏移量
     *
     * 用于new Page<>(current, size)直接开启分页
     * @return 分页总量
     */
    public static Long getSize() {
        return getPage().getSize();
    }

    /**
     * 获取用于拼接到LIMIT sql后的current
     *
     * 直接计算后的current，用在手动提取current size，然后手动拼接Limit sql后缀的情况
     * 用于Mybatis XML中直接使用 LIMIT #{limitCurrent}, #{size}
     * @return (current - 1) * size
     * <pre>
     *     比如：
     *     (一): 前端传来current：1第一页，size：10，那么limitCurrent就是0，即从第一条数据开始查，查10条数据 ===> LIMIT 0, 10
     *     (二): 前端传来current：2第二页，size：10，那么limitCurrent就是10，即从第11条数据开始查，查10条数据 ===> LIMIT 10, 10
     * </pre>
     */
    public static Long getLimitFormatCurrent() {
        return (getCurrent() - 1) * getSize();
    }

}
