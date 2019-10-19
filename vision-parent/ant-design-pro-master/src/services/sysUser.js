import request from '@/utils/request';
//查询
export async function getUser(params) {
    return request("/vision/sysUser/doFindPageObjects",{
        method: "POST",
        body: params,
    });
}



//修改
export async function updateUser(params) {
    
    return request("/vision/sysUser/doUpdateObject",{
        method: "POST",
        body: params,
    });
}

//修改时查询
export async function getSysUserById(params) {
    return request("/vision/sysUser/doFindUserByIdWeb",{
        method: "POST",
        body: params,
    });
}

//修改时查询
export async function addSysUser(params) {
    return request("/vision/sysUser/doSaveObject",{
        method: "POST",
        body: params,
    });
}
//查询角色名称
export async function getRoleName(params) {
    return request("/vision/sysUser/doFindRoleCheckbox",{
        method: "POST",
        body: params,
    });
}

//查询所有角色名称
export async function getRoleNameAll(params) {
    return request("/vision/sysUser/doFindRoleCheckboxAll",{
        method: "POST",
        body: params,
    });
}


