import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col,TreeSelect } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import StandardTable from '@/components/StandardTable/indexNatice';
import cookie from 'react-cookies';


const { TextArea } = Input;
const Option = Select.Option;
const { SHOW_PARENT } = TreeSelect;
@connect(({sysRole, loading }) => ({
    sysRole,
    loading: loading.models.sysRole,
}))
@Form.create()

class SysRoleDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            flag:1,
            value: '所有菜单',
            meusIds:[]

         };
    }



    
    showDrawer=()=>{
        const {sysRole: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'sysRole/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'sysRole/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {sysRole: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("menuIds",this.state.meusIds);
                //发起请求
                formDataSubmit(dispatch,'sysRole',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    
    onChange = value => {
     
    console.log('onChange ', value);

    this.setState(
        this.state.meusIds=value
    )
    const {sysRole: { treeVisible}, loading,dispatch } = this.props;
    if(treeVisible){
        dispatch({
            type:"sysRole/getMenus",
            payload:{
                ...value
            }
        }),

        dispatch({
            type:"sysRole/setTreeVisible",
            payload:!treeVisible,
           
        })
    }
   
   
    this.setState({ value });
  };


    render() {
        const { flag } = this.state;
        const {form: {getFieldDecorator} ,sysRole: { drawerVisible, cusRow,treeData}, dispatch } = this.props;
        const { data, ok} = cusRow;
        //设置树形控件参数
        const tProps = {
            treeData,
            value: this.state.value,
            onChange: this.onChange,
            treeCheckable: true,
            showCheckedStrategy: SHOW_PARENT,
            searchPlaceholder: '菜单管理',
            style: {
              width: 300,
            },
          };
        return(

            <Drawer 
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}>
                 <Form  labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit} >
                <div className={styles.titleSeting}>角色信息</div>
                <br/><br/>

                <Row >

                <Col md={6} sm={24} >
                        <Form.Item label='角色名称' >
                            {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='备注' >
                            {getFieldDecorator('note', { rules: [{ ...rules.required  }],initialValue:ok?data["note"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <div className={styles.titleSeting}>菜单信息</div>
                <div>

                         <TreeSelect {...tProps} />

                </div>
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
        )
    }


}
export default SysRoleDrawer;