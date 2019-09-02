import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';

const { TextArea } = Input;
const Option = Select.Option;
const { MonthPicker, RangePicker } = DatePicker;

@Form.create()

@connect(({symptomType, loading }) => ({
    symptomType,
    loading: loading.models.symptomType,
}))


class SymptomTypeDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=()=>{
        const {symptomType: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'symptomType/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'symptomType/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {symptomType: {  traRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = traRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1);
                //发起请求
                formDataSubmit(dispatch,'symptomType',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


    render() {
        const {form: {getFieldDecorator} ,symptomType: { drawerVisible, traRow}, dispatch } = this.props;
        const { data, ok} = traRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} onSubmit={this.handleSubmit} >
                    <Form.Item label='项目名称' >
                        {getFieldDecorator('title', { rules: [{ ...rules.required  }],initialValue:ok?data["title"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='项目描述' >
                        {getFieldDecorator('describes', { rules: [{ ...rules.required  }],initialValue:ok?data["describes"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>
                    <Form.Item label='是否生效' >
                        {getFieldDecorator('state', { rules: [{ ...rules.required  }],initialValue:ok?data["state"]:''
                        })(
                            <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
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

export default SymptomTypeDrawer;