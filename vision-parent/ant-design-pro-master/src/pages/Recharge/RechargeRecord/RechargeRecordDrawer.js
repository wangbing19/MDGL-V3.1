import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';
import { routerRedux } from 'dva/router';

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
        //清空表单
        this.props.form.resetFields();

        // this.props.dispatch(routerRedux.push({ 
        //     pathname: '/recharge/rechargeRecord/rechargeRecord',
        //     params: {
        //         customerId:row.id
        //     }
        // }))
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {rechargeRecord: {  customer, selectedRows }, dispatch } = this.props;
                // const { data, ok} = traRow;4
                const formData = formatData(fieldsValue);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",customer["orgId"]);
                //发起请求
                formDataSubmit(dispatch,'rechargeRecord',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


    render() {
        const {form: {getFieldDecorator} ,rechargeRecord: { drawerVisible, customer}, dispatch } = this.props;
        return (
            <Drawer
            title={"充值"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 6 }} wrapperCol={{ span: 17 }} onSubmit={this.handleSubmit} >
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('customerId', { rules: [{ ...rules.required  }],initialValue:customer["id"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('orgId', { rules: [{ ...rules.required  }],initialValue:customer["orgId"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('recActivityPushId', { rules: [{ ...rules.required  }]//,initialValue:customer["orgId"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='姓名' >
                        {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:customer["name"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='充值活动名称' >
                        {getFieldDecorator('rechargeType', { rules: [{ ...rules.required  }]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='账户金额' >
                        {getFieldDecorator('money', { rules: [{ ...rules.required  }]
                        })(
                            <InputNumber maxLength={10} style={{width:'100%'}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='客户充值金额' >
                        {getFieldDecorator('rechargeAmount', { rules: [{ ...rules.required  }]
                        })(
                            <InputNumber maxLength={10} style={{width:'100%'}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='赠送金额' >
                        {getFieldDecorator('presentedAmount', { rules: [{ ...rules.required  }]
                        })(
                            <InputNumber maxLength={10} style={{width:'100%'}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='充值的次数' >
                        {getFieldDecorator('practiceTimes', { rules: [{ ...rules.required  }]
                        })(
                            <InputNumber maxLength={10} style={{width:'100%'}}/>
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