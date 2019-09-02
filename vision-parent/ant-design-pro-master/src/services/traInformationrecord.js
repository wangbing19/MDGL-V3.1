import request from '@/utils/request';
//查询
export async function getTraInfor(params) {
    return request("/vision/traInformationrecord/getTraInfor",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteTraInfor(params) {
    return request("/vision/traInformationrecord/deleteTraInfor",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addTraInfor(params) {
    return request("/vision/traInformationrecord/addTraInfor",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateTraInfor(params) {
    return request("/vision/traInformationrecord/updateTraInfor",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getTraInforById(params) {
    return request("/vision/traInformationrecord/getTraInforById",{
        method: "POST",
        body: params,
    });
}
//基于用户id查询课程信息
export async function getByCustomerId(params) {
    return request("/vision/traInformationrecord/getByCustomerId",{
        method: "POST",
        body: params,
    });
}
