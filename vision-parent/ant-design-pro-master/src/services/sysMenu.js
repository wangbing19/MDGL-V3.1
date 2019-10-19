import request from '@/utils/request';


//查询
export async function getSysMenuAll(params) {
    return request("/vision/sysMenu/doFindMenu",{
        method: "POST",
        body: params,
    });
}

//查询一个
export async function findMenusOne(params) {
    return request("/vision/sysMenu/doFindMenuOne",{
        method: "POST",
        body: params,
    });
}



//菜单信息
export async function doFindMenuList(params) {
    return request("/vision/sysMenu/doFindMenuList",{
        method: "POST",
        body: params,
    });
}

//修改
export async function updateMenu(params) {
    return request("/vision/sysMenu/doUpdateMenu",{
        method: "POST",
        body: params,
    });
}

//添加
export async function addSysMenu(params) {
    return request("/vision/sysMenu/doInsertMenu",{
        method: "POST",
        body: params,
    });
}



//删除
export async function deleteMenus(params) {
    return request("/vision/sysMenu/doDeleteMenu",{
        method: "POST",
        body: params,
    });
}
