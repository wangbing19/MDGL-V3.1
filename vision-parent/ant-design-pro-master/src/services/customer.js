import request from '@/utils/request';
//查询
export async function getCustomer(params) {
    return request("/vision/customer/getCustomer",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteCustomer(params) {
    return request("/vision/customer/deleteCustomer",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addCustomer(params) {
    return request("/vision/customer/addCustomer",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateCustomer(params) {
    return request("/vision/customer/updateCustomer",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getCustomerById(params) {
    return request("/vision/customer/getCustomerById",{
        method: "POST",
        body: params,
    });
}
//修改用户状态
export async function updateCustomerState(params) {
    return request("/vision/customer/updateCustomerState",{
        method: "POST",
        body: params,
    });
}
//根据咨询表id查询用户信息
export async function getCustomerByConsultationId(params) {
    return request("/vision/customer/getCustomerByConsultationId",{
        method: "POST",
        body: params,
    });
}

//暂定功能（未）
// 基于充值记录表返回信息用户id修改金额,余额及充值次数
// 基于训练记录表返回信息更改训练次数