import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';

import cookie from 'react-cookies';

const { TextArea } = Input;
const Option = Select.Option;
@Form.create()
@connect(({expert, loading }) => ({
    expert,
    loading: loading.models.expert,
}))
class ExpertDrawer extends Component{
    constructor(props) {
        super(props);
        this.state = { 
            flag:1,
         };
    }

    showDrawer=()=>{
        const {expert: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'expert/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'expert/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


      //表单验证成功后的回调函数
      handleSubmit = (e) => {
      
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
        
            if (!err) {
                const {expert: {  expertRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = expertRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
             //   formData.append("organizationId",1)
                //发起请求
                formDataSubmit(dispatch,'expert',formData);
              
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    render() {
        const {form: {getFieldDecorator} ,expert: { drawerVisible, expertRow}, dispatch } = this.props;
        const { data, ok} = expertRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >

                <Form labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit}>
                    <div className={styles.titleSeting}>
                        专家基本信息
                    </div>
                    <br/><br/>


                    <Form.Item label='专家姓名' >
                        {getFieldDecorator('expertName', { rules: [{ ...rules.required  }],initialValue:ok?data["expertName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='专家电话' >
                        {getFieldDecorator('expertTel', { rules: [{ ...rules.required  }],initialValue:ok?data["expertTel"] :''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    
                    <Form.Item label='预约时间' >
                        {getFieldDecorator('appointmentTime', { rules: [{ ...rules.required  }],initialValue:ok?data["appointmentTime"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    <Form.Item label='专家信息' >
                        {getFieldDecorator('expertMessage', { rules: [{ ...rules.required  }],initialValue:ok?data["expertMessage"]:''
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
        )
    }
}


export default ExpertDrawer;