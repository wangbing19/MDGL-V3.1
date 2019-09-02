import { object } from "prop-types";
import { message, TreeSelect } from 'antd';


/**
 * 封装表单提交FormData参数对象
 * @param {*} data 表单数据
 * @param {*} objName 表单数据后台封装对象名称（前缀）
 * @param {*} noPrefixKey 不需要添加前缀的数据在表单数据中的key
 */
export function formatData(data,objName,id,...noPrefixKey){
    var formData = new FormData();
    if(noPrefixKey){
        noPrefixKey.map(va=>{
            formData.append(va,data[va]);
        })
    }
    for (let k of Object.keys(data)) {
        if(noPrefixKey.indexOf(k) == -1){//不需要加前缀的key
            const dataK = k;
            if(objName && objName !=''){//如果有前缀
                // if(k.includes('parent')){//如果key有parent，就转化为objname.objname.属性名
                //     k = k.substring(6,k.length);//截取属性名
                //     k = k.substring(0,1).toLowerCase()+k.substring(1);//首字符小写
                //     k = objName+'.'+objName+'.'+k;
                //     formData.append(k,data[dataK]);
                // }else{
                    k = objName+'.'+k;
                    formData.append(k,data[dataK]);
                // }
                
            }else{
                formData.append(k,data[k]);
            } 
        }   
    }

    if( id && id !== ''){
        formData.append('id',id);
    }
    
    return formData;
}
/**
 * 将对象中的时间格式化
 * @param {*} data 数据对象
 * @param {*} formart 时间格式
 */
export function FormdateFormat(data,formart){
    let newData = new Object();
    //表单数据的时间格式化
    if(formart){
        for(let k of Object.keys(data)){
            
           if(data[k] instanceof Object && data[k]._d){
            //    alert(data[k])
            newData={
                    ...data,
                    ...newData,
                    [k]:data[k].format(formart),
                }
           }
        }
    }
    var result = {};
    Object.assign(result,data,newData);
    return result;
}

/**
 * 表单时间格式化并提交
 * @param {*Object} dispatch 链接modle
 * @param {*Object} addType 添加方法
 * @param {*Object} upData 修改方法
 * @param {*Object} formData 表单数据
 * @param {*any} rowId 编辑的时候数据行id
 * @param {*string} formDataObjectName 表单数据后台对象名称(前缀)
 * @param {*list} noPrefixKey 不需要加前缀的数据的key
 * @param {*string} formart 时间格式
 */
export function formDataSubmit(dispatch,objName,formData){
    const idKey = 'id';
    //rowId存在的话就是编辑，否则就是添加
    if(formData.get(idKey) && formData.get(idKey) != ''){
        dispatch({
            type:objName+'/update',
            payload:formData,
            callback:response=>{
                message.info(response.msg);
            }
        });
    }else {
        dispatch({
            type:objName+'/add',
            payload:formData,
            callback:response=>{
                message.info(response.msg);
            }
        });
    }
}

 /**
  * 删除表格数据
  * @param {*} idKey 
  * @param {*} id
  * @param {*} type 
  * @param {*} dispatch 
  */
 export function deleteData(id,type,dispatch){
    var formData = new FormData();
    formData.append("id", id);
    formData.append("orgId", 1);
    dispatch({
        type:type+'/remove',
        payload:formData,
        callback:response=>{
            message.info(response.msg);
        }
    });
}

export function renderTreeNodes(data){
    const TreeNode = TreeSelect.TreeNode;
    data.map((item) => {
        if (item.children) {//如果字节点存在的话就递归创建子节点
            //是否有下一个节点，有的话就是文件夹图标没有就是文件图标
            return (
                <TreeNode title={item.text} key={item.id} dataRef={item} value={item.id} isLeaf={item.leaf}>
                    {renderTreeNodes(item.children)}
                </TreeNode>
            );
        }else{
            return <TreeNode title={item.text}  value={item.id} key={item.id} isLeaf={item.leaf}  dataRef={item} />;
        }
    })
}