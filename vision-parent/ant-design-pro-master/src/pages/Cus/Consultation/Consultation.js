import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip } from 'antd';



@connect(({consultation, loading }) => ({
    consultation,
    loading: loading.models.consultation,
}))

class Consultation extends Component {
    constructor(props) {
        super(props);
        this.state = { };
    }

    componentDidMount=()=>{
        this.getConsultation();
    }

    getConsultation=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"consultation/fetch",
            payload:{
                pageCurrent:1,
                orgId:0,
                pageSize:20,
                ...value,
            }
        });
    }

    columns=[
        {
            title: '序号',
            dataIndex: 'time',
            render:(text,record,index)=>`${index+1}`,
            width: "5%",
        },
        {
            title: '姓名',
            dataIndex: 'name',
            width: "15%",
        },
        {
            title: '联系电话',
            dataIndex: 'tel',
            width: "15%",
        },
        {
            title: '裸眼远视力',
            dataIndex: 'ld',
            width: "15%",
            render: (text,row) => (
                <div>
                   {row.ld}/{row.rd}
                </div>
            ),
        },
        {
            title: '矫正远视力',
            dataIndex: 'lcva',
            width: "15%",
            render: (text,row) => (
                <div>
                   {row.lcva}/{row.rcva}
                </div>
            ),
        },
        {
            title: '咨询导师',
            dataIndex: 'tel',
            width: "15%",
        },
        {
            title: '时间',
            dataIndex: 'gmtModified',
            width: "15%",
        },
        {
            title: '操作',
            width: "5%",
            render: (text,row) => (
                <div>
                   
                </div>
            ),
        },
    ]
    
    ghdsjf=(row)=>{
        debugger;
    }

    render() {
        const { consultation: { data }, loading, dispatch} = this.props;
        const records = data.list;
        return (
            <div>
               <Table
                    columns={this.columns}
                    loading={loading}
                    dataSource={records}
               />
            </div>
        );
    }
}

export default Consultation;