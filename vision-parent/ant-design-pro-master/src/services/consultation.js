import request from '@/utils/request';

export async function getConsultation(params) {
    return request("/consultation/getConsultation",{
        method: "POST",
        body: params,
    });
}