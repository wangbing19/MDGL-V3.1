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

@connect(({consultation, loading }) => ({
    consultation,
    loading: loading.models.consultation,
}))


class ConsultationDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            flag:1,
         };
    }

    showDrawer=()=>{
        const {consultation: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'consultation/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'consultation/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {consultation: {  dicRow, selectedRows, sysId }, dispatch } = this.props;
                const { data, ok} = dicRow;
                //封装表单数据对象
                const formData = formatData(fieldsValue,'consultation',data["consultation.id"]);
                formData.append('sysId',sysId);
                //发起请求
                formDataSubmit(dispatch,'consultation',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    onChange=(e)=>{
        this.setState({
            flag:e.target.value,
        })
    }


    render() {
        const { flag } = this.state;
        const {form: {getFieldDecorator} ,consultation: { drawerVisible, cusRow}, dispatch } = this.props;
        const { data, ok} = cusRow;
        let display = "block";
        if(ok&&!data["consultation.parentName"]||flag==0){
            display = "none";
        }
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'30%'}
            >
            <Form  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} onSubmit={this.handleSubmit} >
                <Form.Item label='请选择：' style={{display: ok?"none":"block"}}>
                    {getFieldDecorator('flag', { initialValue:ok?data["consultation.flag"]:flag
                        })(
                            <Radio.Group onChange={this.onChange}>
                                <Radio value={0}>类型</Radio>
                                <Radio value={1}>数据</Radio>
                            </Radio.Group>
                    )}
                </Form.Item>
                <Form.Item label='字典名称：' >
                    {getFieldDecorator(flag==1?'name':'parentName', { rules: [{ ...rules.required }],initialValue:ok?data["consultation.dictionaryName"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <Form.Item label='字典值：' >
                    {getFieldDecorator('dictionaryValue', { rules: [{ ...rules.required  }],initialValue:ok?data["consultation.dictionaryValue"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <Form.Item label='排序或等级：' >
                    {getFieldDecorator('levelOrder', { rules: [{ ...rules.required, ...rules.number  }],initialValue:ok?data["consultation.levelOrder"]:''
                    })(
                        <InputNumber style={{width:"100%"}} />
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

export default ConsultationDrawer;