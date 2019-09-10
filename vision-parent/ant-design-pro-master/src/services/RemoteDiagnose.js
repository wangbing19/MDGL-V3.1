import request from '@/utils/request';
//查询
export async function getRemoteDiagnose(params) {
    return request("/vision/dia/RDMenus",{
        method: "POST",
        body: params,
    });
}



export async function getRemoteDiagnoseUser(params) {
    return request("/vision/dia/userNme",{
        method: "POST",
        body: params,
    });
}


//修改订单状态
export async function updateState(params) {
    return request("/vision/dia/valid",{
        method: "POST",
        body: params,
    });
}

//根据门店用户id查询症状信息
export async function getExpSymptomsDescribed(params) {
    return request("/vision/sym/find",{
        method: "POST",
        body: params,
    });
}
//用户添加症状描述
export async function getAddExpSymptomsDescribed(params) {
    return request("/vision/sym/add",{
        method: "POST",
        body: params,
    });
}

//用户界面查询专家回复信息
export async function getExpExpertReply(params) {
    return request("/vision/rep/inquire",{
        method: "POST",
        body: params,
    });
}

//用户界面查询订单信息用于修改
export async function getExpRemoteDiagnoseOne(params) {
    return request("/vision/dia/inquire",{
        method: "POST",
        body: params,
    });
}



//查询所有生效专家名称
export async function getRemoteDiagnoseNameAll(params) {
    return request("/vision/exp/find/ztree",{
        method: "POST",
        body: params,
    });
}

//修改用户界面诊断订单
export async function updateremoteDiagnose(params) {
    return request("/vision/dia/modifier",{
        method: "POST",
        body: params,
    });
}
//添加用户界面诊断订单
export async function addremoteDiagnose(params) {
    return request("/vision/dia/add",{
        method: "POST",
        body: params,
    });
}




