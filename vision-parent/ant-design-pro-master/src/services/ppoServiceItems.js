import request from '@/utils/request';

//查询
export async function getPpoServiceItems(params) {
    return request("/vision/PpoServiceItems/doFindServiceItems",{
        method: "POST",
        body: params,
    });
}
//根据idc查询查询
export async function getPpoServiceItemsById(params) {
   
    return request("/vision/PpoServiceItems/doFindServiceItemOne",{
        method: "POST",
        body: params,
    });
}
//根据idc查询查询
export async function addPpoServiceItems(params) {
    return request("/vision/PpoServiceItems/doSaveServiceItems",{
        method: "POST",
        body: params,
    });
}
//根据idc查询查询
export async function updatePpoServiceItems(params) {
    return request("/vision/PpoServiceItems/doupdeteServiceItems",{
        method: "POST",
        body: params,
    });
}

