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

@connect(({diagnose, loading }) => ({
    diagnose,
    loading: loading.models.diagnose,
}))


class DiagnoseDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=(e)=>{
        const {diagnose: { drawerVisible, customer }, dispatch} = this.props;
        dispatch({
            type:'diagnose/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'diagnose/clearFeomData',
        })
        
        //清空表单
        this.props.form.resetFields();
     
        if(e==1&&customer["id"]){
            this.props.dispatch(routerRedux.push({ 
                pathname: '/cus/diagnose/diagnose',
                params:{
                    name:customer["name"]
                }
            }))
        }
        dispatch({
            type:'diagnose/setCustomer',
            payload:{},
        })
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {diagnose: {  diaRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = diaRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1)
                //发起请求
                formDataSubmit(dispatch,'diagnose',formData);
                //关闭抽屉
                this.showDrawer(1);
            }
        });
    }


    render() {
        const { oculopathyOtherIsCan, fVisionConditionIsCan, mVisionConditionIsCan, eyeProjectOtherIsCan, eyePositionOtherIsCan  } = this.state;
        const {form: {getFieldDecorator} ,diagnose: { drawerVisible, diaRow, customer}, dispatch } = this.props;
        const { data, ok} = diaRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >
            <Form  labelCol={{ span: 10 }} wrapperCol={{ span: 13 }} onSubmit={this.handleSubmit} >
                <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('customerId', { rules: [{ ...rules.required  }],initialValue:ok?data["customerId"]:customer["id"]
                        })(
                            <Input   />
                        )}
                </Form.Item>
                <div className={styles.titleSeting}>验光</div>
                <br/><br/>
                <div>1.右眼</div>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='（' >
                            {getFieldDecorator('rDs', { rules: [{ ...rules.required  }],initialValue:ok?data["rds"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='）DS + （' >
                            {getFieldDecorator('rDc', { rules: [{ ...rules.required  }],initialValue:ok?data["rdc"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='）DC + （' >
                            {getFieldDecorator('rX', { rules: [{ ...rules.required  }],initialValue:ok?data["rx"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                    </Row><Row>
                    <Col md={6} sm={24} >
                        <Form.Item label='→  视力：（' >
                            {getFieldDecorator('rD', { rules: [{ ...rules.required  }],initialValue:ok?data["rd"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                </Row>
                <div>2.左眼</div>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='（' >
                            {getFieldDecorator('lDs', { rules: [{ ...rules.required  }],initialValue:ok?data["lds"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='）DS + （' >
                            {getFieldDecorator('lDc', { rules: [{ ...rules.required  }],initialValue:ok?data["ldc"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='）DC + （' >
                            {getFieldDecorator('lX', { rules: [{ ...rules.required  }],initialValue:ok?data["lx"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                    </Row><Row>
                    <Col md={6} sm={24} >
                        <Form.Item label='°）→  视力：（' >
                            {getFieldDecorator('lD', { rules: [{ ...rules.required  }],initialValue:ok?data["ld"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={1} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                </Row>
                <Row >
                    <Col md={5} sm={24}>
                        <Form.Item label='3.瞳距: ' >
                            {getFieldDecorator('pupilDistance', { rules: [{ ...rules.required  }],initialValue:ok?data["pupilDistance"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='mm' />
                    </Col>
                </Row>
                <div>4.配镜：</div>
                <Row >
                    <Col md={5} sm={24}>
                        <Form.Item label='右眼（度数' >
                            {getFieldDecorator('rGlassesD', { rules: [{ ...rules.required  }],initialValue:ok?data["rglassesD"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={5} sm={24}>
                        <Form.Item label='°， 散光' >
                            {getFieldDecorator('rGlassesDc', { rules: [{ ...rules.required  }],initialValue:ok?data["rglassesDc"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                    </Row><Row>
                    <Col md={5} sm={24}>
                        <Form.Item label='左眼（度数' >
                            {getFieldDecorator('lGlassesD', { rules: [{ ...rules.required  }],initialValue:ok?data["lglassesD"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={5} sm={24}>
                        <Form.Item label='°， 散光' >
                            {getFieldDecorator('lGlassesDc', { rules: [{ ...rules.required  }],initialValue:ok?data["lglassesDc"]:''
                            })(
                                <InputNumber style={{width:"100%"}}   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={2} sm={24} >
                        <Form.Item label='°）' />
                    </Col>
                </Row>
                <div>5.眼位检查</div>
                <Form.Item label='' >
                    {getFieldDecorator('eyePositionExamination', { rules: [{ ...rules.required  }],initialValue:ok?data["eyePositionExamination"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>6.同视视</div>
                <Form.Item label='' >
                    {getFieldDecorator('synoptophore', { rules: [{ ...rules.required  }],initialValue:ok?data["synoptophore"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>7.融合视</div>
                <Form.Item label='' >
                    {getFieldDecorator('fusionvision', { rules: [{ ...rules.required  }],initialValue:ok?data["fusionvision"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>8.立体视</div>
                <Form.Item label='' >
                    {getFieldDecorator('stereopsis', { rules: [{ ...rules.required  }],initialValue:ok?data["stereopsis"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>9.屈光不正</div>
                <Form.Item label='' >
                    {getFieldDecorator('ametropia', { rules: [{ ...rules.required  }],initialValue:ok?data["ametropia"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>10.弱视程度</div>
                <Form.Item label='' >
                    {getFieldDecorator('amblyopiaDegree', { rules: [{ ...rules.required  }],initialValue:ok?data["amblyopiaDegree"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>11.弱视性质</div>
                <Form.Item label='' >
                    {getFieldDecorator('amblyopia', { rules: [{ ...rules.required  }],initialValue:ok?data["amblyopia"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>12.弱视预后</div>
                <Form.Item label='' >
                    {getFieldDecorator('amblyopiaPrognosis', { rules: [{ ...rules.required  }],initialValue:ok?data["amblyopiaPrognosis"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>13.斜视</div>
                <Form.Item label='' >
                    {getFieldDecorator('strabismus', { rules: [{ ...rules.required  }],initialValue:ok?data["strabismus"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>14.视光其他诊断</div>
                <Form.Item label='' >
                    {getFieldDecorator('visionOther', { rules: [{ ...rules.required  }],initialValue:ok?data["visionOther"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>15.训练过程家长配合事项</div>
                <Form.Item label='' >
                    {getFieldDecorator('parentCooperationDuringTraining', { rules: [{ ...rules.required  }],initialValue:ok?data["parentCooperationDuringTraining"]:''
                    })(
                        <Input.TextArea rows={4}/>
                    )}
                </Form.Item>
                <Row>
                    <Col md={12} sm={24}>
                        <Form.Item label='咨询导师' >
                            {getFieldDecorator('diagnostics', { rules: [{ ...rules.required  }],initialValue:ok?data["diagnostics"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='时间' >
                            {getFieldDecorator('gmtModified', { initialValue:moment(moment().format(dateFormat.day_hour), dateFormat.day_hour)
                            })(
                                <DatePicker disabled format="YYYY-MM-DD HH:mm:ss" />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
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

export default DiagnoseDrawer;