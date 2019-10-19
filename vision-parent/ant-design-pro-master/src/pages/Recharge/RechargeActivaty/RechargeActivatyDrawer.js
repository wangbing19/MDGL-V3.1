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

@connect(({rechargeActivaty, loading }) => ({
    rechargeActivaty,
    loading: loading.models.rechargeActivaty,
}))


class RechargeActivatyDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=()=>{
        const {rechargeActivaty: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'rechargeActivaty/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'rechargeActivaty/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {rechargeActivaty: {  activatyRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = activatyRow;
                fieldsValue = {
                    ...fieldsValue,
                    "activityStartTime":fieldsValue['activityStartTime'].format('YYYY/MM/DD HH:mm:ss'),
                    "activityEndTime":fieldsValue['activityEndTime'].format('YYYY/MM/DD HH:mm:ss'),
                }
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1);
                //发起请求
                formDataSubmit(dispatch,'rechargeActivaty',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


    render() {
        const {form: {getFieldDecorator} ,rechargeActivaty: { drawerVisible, activatyRow}, dispatch } = this.props;
        const { data, ok} = activatyRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 6 }} wrapperCol={{ span: 17 }} onSubmit={this.handleSubmit} >
                    <Form.Item label='活动名称' >
                        {getFieldDecorator('title', { rules: [{ ...rules.required  }],initialValue:ok?data["title"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='活动描述' >
                        {getFieldDecorator('describes', { rules: [{ ...rules.required  }],initialValue:ok?data["describes"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>
                    <Form.Item label='充值金额' >
                        {getFieldDecorator('payAmount', { rules: [{ ...rules.required  }],initialValue:ok?data["payAmount"]:''
                        })(
                            <InputNumber  maxLength={10}  />
                        )}
                    </Form.Item>
                    <Form.Item label='赠送金额' >
                        {getFieldDecorator('presentedAmount', { rules: [{ ...rules.required  }],initialValue:ok?data["presentedAmount"]:''
                        })(
                            <InputNumber  maxLength={10} />
                        )}
                    </Form.Item>
                    <Form.Item label='活动开始时间' >
                        {getFieldDecorator('activityStartTime', { rules: [{ ...rules.required  }],initialValue:moment(ok?moment(data["activityStartTime"]).format(dateFormat.day_hour):moment().format(dateFormat.day_hour), dateFormat.day_hour)
                        })(
                            <DatePicker format={dateFormat.day_hour} style={{width:'100%'}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='活动结束时间' >
                        {getFieldDecorator('activityEndTime', { rules: [{ ...rules.required  }],initialValue:moment(ok?moment(data["activityEndTime"]).format(dateFormat.day_hour):moment().format(dateFormat.day_hour), dateFormat.day_hour)
                        })(
                            <DatePicker format={dateFormat.day_hour} style={{width:'100%'}}/>
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

export default RechargeActivatyDrawer;