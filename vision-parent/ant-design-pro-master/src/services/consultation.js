import request from '@/utils/request';
//查询
export async function getConsultation(params) {
    return request("/consultation/getConsultation",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteConsultation(params) {
    return request("/consultation/deleteConsultation",{
        method: "POST",
        body: params,
    });
}