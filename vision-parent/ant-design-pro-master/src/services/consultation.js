import request from '@/utils/request';
//查询
export async function getConsultation(params) {
    return request("/vision/consultation/getConsultation",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteConsultation(params) {
    return request("/vision/consultation/deleteConsultation",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addConsultation(params) {
    return request("/vision/consultation/addConsultation",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateConsultation(params) {
    return request("/vision/consultation/updateConsultation",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getConsultationById(params) {
    return request("/vision/consultation/getConsultationById",{
        method: "POST",
        body: params,
    });
}