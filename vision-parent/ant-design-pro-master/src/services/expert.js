import request from '@/utils/request';


//查询
export async function getExpert(params) {

    return request("/vision/exp/find/page",{
        method: "POST",
        body: params,
    });
}


//更据id查询
export async function getExpertById(params) {
      
    return request("/vision/exp/inquire",{
        method: "POST",
        body: params,
    });
}

//添加
export async function addExpert(params) {
      
    return request("/vision/exp/add",{
        method: "POST",
        body: params,
    });
}

//修改
export async function updateExpert(params) {
      
    return request("/vision/exp/modifier",{
        method: "POST",
        body: params,
    });
}



//删除
export async function deleteExpert(params) {
      
    return request("/vision/exp/del",{
        method: "POST",
        body: params,
    });
}
