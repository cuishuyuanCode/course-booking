package com.course.booking.service.impl;

import com.course.booking.common.entity.Constants;
import com.course.booking.common.entity.user.UserConstants;
import com.course.booking.controller.vo.MetaVO;
import com.course.booking.controller.vo.RouterResultVO;
import com.course.booking.controller.vo.RouterVO;
import com.course.booking.dao.RouterMapper;
import com.course.booking.service.RouterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class RouterServiceImpl implements RouterService {

    @Resource
    private RouterMapper routerMapper;

    @Override
    public List<RouterResultVO> getRouters() {
        List<RouterVO> routerVOList = routerMapper.getAllRouters();
        return buildMenus(getChildPerms(routerVOList, 0));
    }

    private List<RouterResultVO> buildMenus(List<RouterVO> routerVOList) {
        List<RouterResultVO> routers = new LinkedList<RouterResultVO>();
        if (!CollectionUtils.isEmpty(routerVOList)) {
            for (RouterVO menu : routerVOList) {
                RouterResultVO router = new RouterResultVO();
                router.setHidden("1".equals(menu.getVisible()));
                router.setName(getRouteName(menu));
                router.setPath(getRouterPath(menu));
                router.setComponent(getComponent(menu));
                router.setQuery(menu.getQuery());
                router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache()), menu.getPath()));
                List<RouterVO> cMenus = menu.getChildren();
                if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                    router.setAlwaysShow(true);
                    router.setRedirect("noRedirect");
                    router.setChildren(buildMenus(cMenus));
                } else if (isMenuFrame(menu)) {
                    router.setMeta(null);
                    List<RouterResultVO> childrenList = new ArrayList<RouterResultVO>();
                    RouterResultVO children = new RouterResultVO();
                    children.setPath(menu.getPath());
                    children.setComponent(menu.getComponent());
                    children.setName(StringUtils.capitalize(menu.getPath()));
                    children.setQuery(menu.getQuery());
                    childrenList.add(children);
                    router.setChildren(childrenList);
                } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                    router.setPath("/");
                    router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon()));
                    List<RouterResultVO> childrenList = new ArrayList<RouterResultVO>();
                    RouterResultVO children = new RouterResultVO();
                    String routerPath = innerLinkReplaceEach(menu.getPath());
                    children.setPath(routerPath);
                    children.setComponent(UserConstants.INNER_LINK);
                    children.setName(StringUtils.capitalize(routerPath));
                    children.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                    childrenList.add(children);
                    router.setChildren(childrenList);
                }
                routers.add(router);
            }
        }
        return routers;
    }


    /**
     * ??????????????????ID?????????????????????
     *
     * @param list     ?????????
     * @param parentId ??????????????????ID
     * @return String
     */
    public List<RouterVO> getChildPerms(List<RouterVO> list, int parentId) {
        List<RouterVO> returnList = new ArrayList<RouterVO>();
        for (Iterator<RouterVO> iterator = list.iterator(); iterator.hasNext(); ) {
            RouterVO t = (RouterVO) iterator.next();
            // ????????????????????????????????????ID,????????????????????????????????????
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }


    /**
     * ????????????
     *
     * @param list ?????????
     * @param t    ?????????
     */
    private void recursionFn(List<RouterVO> list, RouterVO t) {
        // ?????????????????????
        List<RouterVO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (RouterVO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }


    /**
     * ????????????????????????
     */
    private boolean hasChild(List<RouterVO> list, RouterVO t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * ?????????????????????
     */
    private List<RouterVO> getChildList(List<RouterVO> list, RouterVO t) {
        List<RouterVO> tlist = new ArrayList<RouterVO>();
        Iterator<RouterVO> it = list.iterator();
        while (it.hasNext()) {
            RouterVO n = (RouterVO) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }


    /**
     * ?????????????????????
     *
     * @param menu ????????????
     * @return ??????
     */
    public boolean isInnerLink(RouterVO menu) {
        boolean withAny = StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS);
        return menu.getIsFrame().equals(UserConstants.NO_FRAME) && withAny;
    }

    /**
     * ??????????????????
     *
     * @param menu ????????????
     * @return ????????????
     */
    public String getRouteName(RouterVO menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // ???????????????????????????????????????????????????
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }


    /**
     * ???????????????????????????
     *
     * @param menu ????????????
     * @return ??????
     */
    public boolean isMenuFrame(RouterVO menu) {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }


    /**
     * ??????????????????
     *
     * @param menu ????????????
     * @return ????????????
     */
    public String getRouterPath(RouterVO menu) {
        String routerPath = menu.getPath();
        // ????????????????????????
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // ???????????????????????????????????????????????????
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // ???????????????????????????????????????????????????
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }


    /**
     * ??????????????????????????????
     *
     * @return ????????????????????????
     */
    public String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

    /**
     * ??????????????????
     *
     * @param menu ????????????
     * @return ????????????
     */
    public String getComponent(RouterVO menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = UserConstants.INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * ?????????parent_view??????
     *
     * @param menu ????????????
     * @return ??????
     */
    public boolean isParentView(RouterVO menu) {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }
}

