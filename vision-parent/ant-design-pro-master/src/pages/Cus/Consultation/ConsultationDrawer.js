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
                const {consultation: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1)
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
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >
            <Form  labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit} >
                <div className={styles.titleSeting}>基础信息</div>
                <br/><br/>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='姓名' >
                            {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='年龄' >
                            {getFieldDecorator('age', { rules: [{ ...rules.required  }],initialValue:ok?data["age"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='性别' >
                            {getFieldDecorator('gender', { rules: [{ ...rules.required  }],initialValue:ok?data["gender"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='联系方式' >
                            {getFieldDecorator('tel', { rules: [{ ...rules.required  }],initialValue:ok?data["tel"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>主要信息采集</div>
                <br/><br/>
                <span>1.您从什么时候发现视力下降</span>
                <Row >
                    <Col md={8} sm={24}>
                        <Form.Item label='左右眼' >
                            {getFieldDecorator('eye', { rules: [{ ...rules.required  }],initialValue:ok?data["eye"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='年' >
                            {getFieldDecorator('declineTimeYear', { rules: [{ ...rules.required  }],initialValue:ok?data["declineTimeYear"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='月' >
                            {getFieldDecorator('declineTimeMonth', { rules: [{ ...rules.required  }],initialValue:ok?data["declineTimeMonth"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>2.是否到专业医疗机构确诊</span>
                <Form.Item label='' >
                    {getFieldDecorator('diagnose', { rules: [{ ...rules.required  }],initialValue:ok?data["diagnose"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>3.确认为何种病症</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('oculopathy', { rules: [{ ...rules.required  }],initialValue:ok?data["oculopathy"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='其他' >
                            {getFieldDecorator('oculopathyOther', { rules: [{ ...rules.required  }],initialValue:ok?data["oculopathyOther"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>4.发现症状后采用何种矫正方法</span>
                <Form.Item label='' >
                    {getFieldDecorator('correctionMethod', { rules: [{ ...rules.required  }],initialValue:ok?data["correctionMethod"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>5.效果如何</span>
                <Form.Item label='' >
                    {getFieldDecorator('effect', { rules: [{ ...rules.required  }],initialValue:ok?data["effect"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>6.现视力情况</span>
                <Form.Item label='' >
                    {getFieldDecorator('visualAcuity', { rules: [{ ...rules.required  }],initialValue:ok?data["visualAcuity"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <div>遗传因素排查</div>
                <span>7.父亲</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('fVisionCondition', { rules: [{ ...rules.required  }],initialValue:ok?data["fVisionCondition"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='其他眼病 ' >
                            {getFieldDecorator('fOther', { rules: [{ ...rules.required  }],initialValue:ok?data["fOther"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>8.母亲</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('mVisionCondition', { rules: [{ ...rules.required  }],initialValue:ok?data["mVisionCondition"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='其他眼病' >
                            {getFieldDecorator('mOther', { rules: [{ ...rules.required  }],initialValue:ok?data["mOther"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>睡眠状况</div>
                <br/><br/>
                <span>9.睡眠状况</span>
                <Form.Item label='' >
                    {getFieldDecorator('sleepingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["sleepingTime"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <hr/>
                <div className={styles.titleSeting}>用眼习惯采集</div>
                <br/><br/>
                <span>10.用眼项目</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('eyeProject', { rules: [{ ...rules.required  }],initialValue:ok?data["eyeProject"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='用眼项目其他' >
                            {getFieldDecorator('eyeProjectOther', { rules: [{ ...rules.required  }],initialValue:ok?data["eyeProjectOther"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>11.每次看书、作业的时长</span>
                <Form.Item label='' >
                    {getFieldDecorator('readingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["readingTime"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>12.看书距离</span>
                <Form.Item label='' >
                    {getFieldDecorator('readingDistance', { rules: [{ ...rules.required  }],initialValue:ok?data["readingDistance"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>13.单次看电视时长</span>
                <Form.Item label='' >
                    {getFieldDecorator('watchingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["watchingTime"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>14.看电视距离</span>
                <Form.Item label='' >
                    {getFieldDecorator('watchingDistance', { rules: [{ ...rules.required  }],initialValue:ok?data["watchingDistance"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>15.用眼姿势（有无）</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('eyePosition', { rules: [{ ...rules.required  }],initialValue:ok?data["eyePosition"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='其他' >
                            {getFieldDecorator('eyePositionOther', { rules: [{ ...rules.required  }],initialValue:ok?data["eyePositionOther"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>用眼环境</div>
                <br/><br/>
                <span>16.家庭环境光线</span>
                <Form.Item label='' >
                    {getFieldDecorator('homeLightingEnvironment', { rules: [{ ...rules.required  }],initialValue:ok?data["homeLightingEnvironment"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <span>17.教室环境光线</span>
                <Form.Item label='' >
                    {getFieldDecorator('classroomLightingEnvironment', { rules: [{ ...rules.required  }],initialValue:ok?data["classroomLightingEnvironment"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <hr/>
                <div className={styles.titleSeting}>视力检查</div>
                <br/><br/>
                <span>18.裸眼远视力（5米）</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='右眼' >
                            {getFieldDecorator('rD', { rules: [{ ...rules.required  }],initialValue:ok?data["rD"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='左眼' >
                            {getFieldDecorator('lD', { rules: [{ ...rules.required  }],initialValue:ok?data["lD"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>19.矫正远视力（5米）</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='右眼' >
                            {getFieldDecorator('rCva', { rules: [{ ...rules.required  }],initialValue:ok?data["rCva"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='左眼' >
                            {getFieldDecorator('lCva', { rules: [{ ...rules.required  }],initialValue:ok?data["lCva"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <Form.Item label='咨询导师' >
                    {getFieldDecorator('tutor', { rules: [{ ...rules.required  }],initialValue:ok?data["tutor"]:''
                    })(
                        <Input   />
                    )}
                </Form.Item>
                <Form.Item label='时间' >
                    {getFieldDecorator('gmtCreate', { rules: [{   }],initialValue:ok?data["gmtCreate"]:''
                    })(
                        <Input   />
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