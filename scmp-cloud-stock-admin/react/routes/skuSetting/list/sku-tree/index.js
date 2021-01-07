/* eslint-disable eqeqeq */
import React, {useState} from 'react';
import {observer} from 'mobx-react-lite';
import {Tree} from 'choerodon-ui';
import _ from 'lodash';
import './index.less';

const {TreeNode} = Tree;

export default observer(({style, dataSet, skuDataSet, currentCode, setCurrentCode}) => {
  async function onLoadData(treeNode) {
    if (treeNode.props.children || dataSet.queryParameter.parent_id === (treeNode.props.eventKey || treeNode.key)) {
      return true;
    }
    const currentData = dataSet.toData();
    dataSet.setQueryParameter('parent_id', treeNode.props.eventKey || treeNode.key);

    const curCode = dataSet.current ? (dataSet.current).get('id') : '';
    await dataSet.query();
    dataSet.setQueryParameter('parent_id', 0);
    if (dataSet.toData().length > 0) {
      const newData = _.sortBy(dataSet.toData(), 'id');
      dataSet.loadData(currentData.concat(newData));
    } else {
      dataSet.loadData(currentData.concat({
        parentId: treeNode.props.eventKey || treeNode.key,
        LEAF_FLAG: '1',
        ID: 'none',
        NAME: 'none',
      }));
    }
    if (currentCode && dataSet.find(r => r.get('id') === currentCode)) {
      dataSet.current = dataSet.find(r => r.get('id') === currentCode);
      setCurrentCode(false);
    } else {
      dataSet.current = dataSet.find(r => r.get('id') === curCode);
    }
    return true;
  }

  function selectNode(item) {
    dataSet.current = item;
    skuDataSet.query();
  }

  function renderTreeNodes(data) {
    return data?.map(item => {
      const children = dataSet.filter(r => r.get('parentId') === item.get('id'));
      if (children && children.length > 0) {
        return (
          <TreeNode selectable={false} title={<div onClick={() => selectNode(item)}>{item.get('name')}</div>}
                    key={item.get('id')} dataRef={item}>
            {renderTreeNodes(children)}
          </TreeNode>
        );
      }
      return <TreeNode selectable={false} onClick={() => selectNode(item)} isLeaf={item.get('leafFlag')}
                       key={item.get('id')} title={<div onClick={() => selectNode(item)}>{item.get('name')}</div>}
                       dataRef={item}/>;
    });
  }

  // dataSet.find(r => r.get('code') == 0)
  return (
    <div style={{
      height: 'calc(100vh - 129px)',
      overflow: 'auto',
      borderRight: '1px solid #ddd',
      marginRight: '24px',
      marginTop: '-20px',
      marginBottom: '-20px',
      ...style,
    }}
    >
      <Tree onSelect={(selectedKeys) => {
      }} loadedKeys={[]} loadData={onLoadData}>{renderTreeNodes(dataSet.filter(r => r.get('id') == 10001))}</Tree>
    </div>
  );
});
