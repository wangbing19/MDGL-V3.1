import request from '@/utils/request';
//查询
export async function getPreSymptomTypeAll(params) {
    return request("/vision/symptomType/findAllObjects",{
        method: "POST",
        body: params,
    });
}


//更据症状id查询处方信息
export async function findSymptomDesc(params) {

    return request("/vision/symptomDesc/findSymptomDescObjectByid",{
        method: "POST",
        body: params,
    });
}


//所有症状分页查询
export async function findAllObjectsList(params) {

    return request("/vision/symptomType/findAllObjectsList",{
        method: "POST",
        body: params,
    });
}

//修改症状
export async function findSymptomfindSymptomById(params) {

    return request("/vision/symptomType/findSymptomObjectById",{
        method: "POST",
        body: params,
    });
}
//症状修改
export async function updateSymptom(params) {

    return request("/vision/symptomType/updateSymptomObject",{
        method: "POST",
        body: params,
    });
}

//症状添加
export async function addPreSymptomType(params) {

    return request("/vision/symptomType/insertSymptomObject",{
        method: "POST",
        body: params,
    });
}


//症状删除
export async function deletePreSymptomType(params) {

    return request("/vision/symptomType/deleteSymptomObjectById",{
        method: "POST",
        body: params,
    });
}



