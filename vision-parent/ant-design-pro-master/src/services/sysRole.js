import request from '@/utils/request';

//查询
export async function getSysRole(params) {
    return request("/vision/sysRole/doFindRoleAll",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateRole(params) {
    return request("/vision/sysRole/doUpdateRole",{
        method: "POST",
        body: params,
    });
}

//根据id查询消息
export async function getRoleById(params) {
    return request("/vision/sysRole/doFindObjectById",{
        method: "POST",
        body: params,
    });
}

//添加
export async function addRole(params) {
    return request("/vision/sysRole/doInsertRole",{
        method: "POST",
        body: params,
    });
}

//删除
export async function deleteSysRole(params) {
    return request("/vision/sysRole/doDeleteRole",{
        method: "POST",
        body: params,
    });
}