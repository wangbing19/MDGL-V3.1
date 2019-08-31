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

@connect(({rechargeRecord, loading }) => ({
    rechargeRecord,
    loading: loading.models.rechargeRecord,
}))


class RechargeRecordDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=()=>{
        const {rechargeRecord: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'rechargeRecord/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'rechargeRecord/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {rechargeRecord: {  traRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = traRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1);
                //发起请求
                formDataSubmit(dispatch,'rechargeRecord',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


    render() {
        const {form: {getFieldDecorator} ,rechargeRecord: { drawerVisible, traRow}, dispatch } = this.props;
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
                <Form  labelCol={{ span: 6 }} wrapperCol={{ span: 17 }} onSubmit={this.handleSubmit} >
                    <Form.Item label='活动名称' >
                        {getFieldDecorator('title', { rules: [{ ...rules.required  }],initialValue:ok?data["title"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='活动描述' >
                        {getFieldDecorator('describe', { rules: [{ ...rules.required  }],initialValue:ok?data["describe"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>
                    <Form.Item label='充值金额' >
                        {getFieldDecorator('payAmount', { rules: [{ ...rules.required  }],initialValue:ok?data["payAmount"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='赠送金额' >
                        {getFieldDecorator('presentedAmount', { rules: [{ ...rules.required  }],initialValue:ok?data["presentedAmount"]:''
                        })(
                            <Input   />
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

export default RechargeRecordDrawer;