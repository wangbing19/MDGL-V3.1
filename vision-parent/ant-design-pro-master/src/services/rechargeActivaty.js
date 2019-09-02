import request from '@/utils/request';
//查询
export async function getList(params) {
    return request("/vision/rechargeActivaty/findAllRecActivityObjects",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleted(params) {
    return request("/vision/rechargeActivaty/deleteRecActivityById",{
        method: "POST",
        body: params,
    });
}
//添加
export async function add(params) {
    return request("/vision/rechargeActivaty/insertRecActivity",{
        method: "POST",
        body: params,
    });
}
//修改
export async function update(params) {
    return request("/vision/rechargeActivaty/updateRecActivityById",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getById(params) {
    return request("/vision/rechargeActivaty/findRecActivityObject",{
        method: "POST",
        body: params,
    });
}
