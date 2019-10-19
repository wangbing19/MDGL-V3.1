import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';
import BaseDicTree from '@/components/BaseTree/BaseDicTree';
@Form.create()

@connect(({sysMenu, loading }) => ({
    sysMenu,
    loading: loading.models.sysMenu,
}))
class SysMenuDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            sysMenuId:'',
         };
    };

    showDrawer=()=>{
        const {sysMenu: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'sysMenu/setDrawerVisible',
            payload:!drawerVisible,
        });
        dispatch({
            type:'sysMenu/clearFeomData',
        });
        //清空表单
        this.props.form.resetFields();
    };
    onSelect=(e)=>{
      
        const {form: {getFieldDecorator} ,sysMenu: {treeData, drawerVisible, sysMenuRow,menusId}, dispatch } = this.props;
        const { data, ok} = sysMenuRow;
        
        dispatch({
            type:'sysMenu/setMenusId',
            payload:e,
        })
     
      
      
      
      
      };
    
    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {sysMenu: {  sysMenuRow, selectedRows ,menusId}, dispatch } = this.props;
                const { data, ok} = sysMenuRow;
                const formData = formatData(fieldsValue,"",data["id"]);
             
                if(menusId!="" || menusId.length != 0){
              
                    formData.append("parentId", menusId);
                }else{
                    formData.append("parentId", data["parentId"]);
                };
             
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                //formData.append("orgId",1);
                //发起请求
                formDataSubmit(dispatch,'sysMenu',formData);
                dispatch({
                    type:'sysMenu/setMenusId',
                    payload:"",
                });
                //关闭抽屉
                this.showDrawer();
            }
        });
    };

    render() {

        const {form: {getFieldDecorator} ,sysMenu: {treeData, drawerVisible, sysMenuRow}, dispatch } = this.props;
        const { data, ok} = sysMenuRow;
        const treeNodeLists = [treeData]
        return(
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                  <Form  labelCol={{ span: 10 }} wrapperCol={{ span: 13 }} onSubmit={this.handleSubmit} >

                  <Form.Item label='菜单名' >
                        {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                        })(
                            <Input   />
                        )},
                    </Form.Item>
                    <Form.Item label='上级菜单名' >
                        {getFieldDecorator('parventName', { rules: [{ ...rules.required  }],initialValue:ok?data["parventName"]:''
                        })(
                            <Input   />
                        )},
                    </Form.Item>
                    <Form.Item label='资源路径' >
                        {getFieldDecorator('url', { rules: [{ ...rules.required  }],initialValue:ok?data["url"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='排序' >
                        {getFieldDecorator('sort', { rules: [{ ...rules.required  }],initialValue:ok?data["sort"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    <Form.Item label='授权' >
                        {getFieldDecorator('permission', { rules: [{ ...rules.required  }],initialValue:ok?data["permission"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    
                    <Form.Item label='类型' >
                        {getFieldDecorator('type', { rules: [{ ...rules.required  }],initialValue:ok?data["type"]:''
                        })(
                            <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                <Radio value={1}>菜单</Radio>
                                <Radio value={0}>按钮</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>
                   
                    <Form.Item label='选择上级菜单' >
                      

                          
                            <BaseDicTree
                                filterTreeNode="true"
                                onSelect={this.onSelect}
                                treeList={treeNodeLists}
                                defaultExpandedKeys={[treeNodeLists[0].id]}
                         />
                        
                    </Form.Item>
                        
                       

                    <Form.Item label='备注' >
                        {getFieldDecorator('note', { rules: [{ ...rules.required  }],initialValue:ok?data["note"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>
                    <div className={styles.buttons} >
                        <Button onClick={this.showDrawer} className={styles.cancel} >
                            返回
                        </Button>
                        <Button  type="primary" htmlType="submit">
                            提交
                        </Button>
                    </div>
                  </Form>
                  
            </Drawer>
        );
    }   
 
}

export default SysMenuDrawer;