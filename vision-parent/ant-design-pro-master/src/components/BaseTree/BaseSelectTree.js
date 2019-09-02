import React, { Component } from 'react';
import {  TreeSelect } from 'antd';

const TreeNode = TreeSelect.TreeNode;

/**
 * 查询树结构
 * @param {*} treeList 数据内容，要求数据格式，含children
 * @param {*} multiple 支持多选（当设置 treeCheckable 时自动变为 true）,默认为false
 * @param {*} treeDefaultExpandAll 默认展开所有树节点，默认false，即不展开
 * @param {*} onChange 选中树节点时调用此函数
 * @param {*} allowClear 显示清除按钮,默认为false
 * @param {*} otherAttribute 其他属性，如otherAttribute={{showSearch:true}}
 * @param {*} labelInValue 是否把每个选项的 label 包装到 value 中，会把 Select 的 value 类型从 string 变为 {key: string, label: ReactNode} 的格式
 * @param {*} treeCheckable 多选
 * @param {*} value 赋值时输入value={valueId:值},值可以是对象或其他
 * @param {*} treeExpandedKeys 设置展开的树节点
 */
class BaseSelectTree extends Component {
    constructor(props) {
        super(props);
        const value = props.value || {};
        this.state = {
            valueId: value.valueId,
        };
    }

    static getDerivedStateFromProps(nextProps) {
        // Should be a controlled component.
        if ('value' in nextProps) {
            return {
                ...(nextProps.value || {}),
            };
        }
        return null;
    }


    onChange=(valueId, label, extra)=>{
        if (!('value' in this.props)) {
            this.setState({ valueId:valueId });
        }
        this.triggerChange({ valueId, label, extra });
    }
    onSelect=(key, node, extra)=>{
        const onSelect = this.props.onSelect;
        if (onSelect) {
            onSelect(key, node, extra);
        }
    }

    triggerChange = changedValue => {
        // Should provide an event to pass value to Form.
        const onChange = this.props.onChange;
        if (onChange) {
            onChange(Object.assign({}, changedValue));
        }
    };
    
    renderTreeNodes = data => data.map((item) => {
        if (item.children) {//如果字节点存在的话就递归创建子节点
            //是否有下一个节点，有的话就是文件夹图标没有就是文件图标
            return (
                <TreeNode title={item.text} key={item.id} dataRef={item} value={item.id} isLeaf={item.leaf}>
                    {this.renderTreeNodes(item.children)}
                </TreeNode>
            );
        }else{
            return <TreeNode title={item.text}  value={item.id} key={item.id} isLeaf={item.leaf}  dataRef={item} />;
        }
    })
    
    render() {
        const {multiple,  treeDefaultExpandAll,labelInValue,allowClear,otherAttribute,treeCheckable,treeExpandedKeys,placeholder} = this.props;//treeList, 
        let {treeList} =this.props;
        if(!treeList||!treeList.length) treeList=[]
        return (
                <TreeSelect
                    {...otherAttribute}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    placeholder="请选择"
                    allowClear={allowClear}
                    multiple = {multiple}
                    onChange={this.onChange}
                    treeDefaultExpandAll={treeDefaultExpandAll}
                    value = {this.state.valueId}
                    labelInValue={labelInValue}
                    treeCheckable={treeCheckable}
                    treeExpandedKeys={treeExpandedKeys}
                    placeholder={placeholder}
                    onSelect={this.onSelect}
                >
                    {this.renderTreeNodes(treeList)}
                </TreeSelect>
        );
    }
}

export default BaseSelectTree;