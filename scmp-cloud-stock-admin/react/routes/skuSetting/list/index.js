import React, {useContext, useState, useRef} from 'react';
import {
  Table,
  Form,
  Modal,
  Button,
  TextArea,
  TextField,
  DateTimePicker,
  message,
  Select,
  Output,
  Row,
  Col,
  NumberField,
  Transfer
} from 'choerodon-ui/pro';
import {observer, useObserver} from 'mobx-react-lite';
import {StatusTag, ClickText} from '@buildrun/components';
import moment from 'moment';
import {Page, axios, Content, Header} from '@buildrun/boot';
import Store from '../stores';
import './index.less';
import SkuTree from './sku-tree';
import ImportAddress from './import-adress';
import ExportAddress from './export-adress';
import ExportSetting from './export-setting';
import ImportSetting from './import-setting';
import DragBar from '../../../components/drabble-bar';

const {Column} = Table;
const {Option} = Transfer;
export default observer(() => {
  const context = useContext(Store);
  const {skuDataSet, productDataSet, localStore, sourceCodeDataSet, stockCodeDataSet, fulfillDataSet, deliveryTypeDataSet, booleanDataSet} = context;
  const [currentCode, setCurrentCode] = useState(false);
  const rootRef = useRef(null);
  const footer = (okbtn, cancelBtn) => cancelBtn;

  function openModal(dataSet, title, type) {
    const editModalProps = {
      title,
      drawer: true,
      style: {width: 'calc(100% - 3.5rem)'},
      okText: '保存',
    };
    if (type === 'detail') {
      editModalProps.footer = (okbtn, cancelBtn) => cancelBtn;
      editModalProps.cancenlText = '关闭';
    }
    if (type === 'edit') {
      dataSet.current.set('id', skuDataSet.current?.get('id'));
    }
    if (type === 'create') {
      dataSet.create({
        productCode: `${productDataSet.current?.get('name')}/${productDataSet.current?.get('productCode')}`,

      });
      editModalProps.onCancel = () => dataSet.reset();
    }
    const ObserverForm = observer(() => (

      <Form columns={2} dataSet={dataSet} disabled={type === 'detail'}>
        <TextField name="productCode" required/>
        <Select name="sourceCode" required>
          {sourceCodeDataSet.data.map(opt => (
            <Select.Option key={opt.data.value} value={`${opt.data.value}`}>{opt.data.meaning}</Select.Option>
          ))}
        </Select>

        <Select name="deliveryType" required>
          {deliveryTypeDataSet.data.map(opt => (
            <Select.Option key={opt.data.value} value={`${opt.data.value}`}>{opt.data.meaning}</Select.Option>
          ))}</Select>

        <Select name="stockCode" required>
          {stockCodeDataSet.data.map(opt => (
            <Select.Option key={opt.data.value} value={`${opt.data.value}`}>{opt.data.meaning}</Select.Option>
          ))}
        </Select>

        <Transfer name="dcSetting" titles={['履行仓待选', '履行仓已选']} required>
          {fulfillDataSet.map(opt => (
            <Option value={opt.data.value} key={opt.data.value}>{opt.data.meaning}</Option>))}
        </Transfer>

        <TextArea name="dcX" rows={13}/>

        <DateTimePicker required
                        filter={(currentDate, selected) => {
                          if (!skuDataSet.current?.get('effectiveDate')) return true;
                          else {
                            return moment(currentDate).diff(skuDataSet.current?.get('effectiveDate'), 'second') < 0;
                          }
                        }}
                        name="effectiveDate" format="yyyy-MM-dd'T'HH:mm:ss"
        />
        <DateTimePicker required
                        filter={(currentDate, selected) => {
                          if (!skuDataSet.current?.get('inactiveDate')) return true;
                          else {
                            return moment(currentDate).diff(skuDataSet.current?.get('inactiveDate'), 'second') > 0;
                          }
                        }}
                        name="inactiveDate" format="yyyy-MM-dd'T'HH:mm:ss"
        />

        <Select name="active" required>
          {booleanDataSet.data.map(opt => (
            <Select.Option key={opt.data.value} value={`${opt.data.value}`}>{opt.data.meaning}</Select.Option>
          ))}
        </Select>

      </Form>
    ));
    Modal.open({
      onOk: async () => {
        try {
          if (await dataSet.submit()) {
            dataSet.query();
            return true;
          } else {
            return false;
          }
        } catch (e) {
          //
          return false;
        }
      },
      children: (
        <ObserverForm/>
      ),
      ...editModalProps,
    });
  }

  function getTable(dataSet, title) {
    return (
      <Table queryBar="none" dataSet={dataSet}>
        <Column name="productCode"/>
        <Column name="sourceCode"/>
        <Column name="deliveryType"/>
        <Column name="addressCode"/>
        <Column name="dc0" width={300}
                renderer={({record, text}) => `${text}/${record.get('dc1')}/${record.get('dc2')}/${record.get('dc3')}/${record.get('dc4')}/${record.get('dc5')}/${record.get('dc6')}/${record.get('dc7')}/${record.get('dc8')}/${record.get('dc9')}`}/>
        <Column name="active"/>
        <Column width={180} name="effectiveDate" tooltip="overflow"/>
        <Column width={180} name="inactiveDate" tooltip="overflow"/>
        <Column width={100} renderer={({record}) => record.get('editFlag') ? (
          <Button color="primary" icon="mode_edit" onClick={() => openModal(dataSet, title, 'edit')}>编辑</Button>) : (
          <Button color="primary" icon="book" onClick={() => openModal(dataSet, title, 'detail')}>详情</Button>)}/>
      </Table>
    );
  }

  return (
    <Page>
      <Header>

        <Button
          icon="import_export"
          /* onClick={() => {
             Modal.open({
               title: '导入配置',
               children: (<ImportSetting />),
               ...modalProps,
             });
           }}*/
        >导入配置
        </Button>
        <Button
          icon="export"
          /*onClick={() => {
            Modal.open({
              title: '导出配置',
              children: (<ExportSetting />),
              ...modalProps,
            });
          }}*/
        >导出配置
        </Button>
        <Button
          icon="refresh"
          onClick={async () => {
            await skuDataSet.loadData([]);
            await skuDataSet.query();

          }}
        >刷新
        </Button>
      </Header>
      <Content style={{height: 'calc(100vh - 129px)'}}>
        <div className="logistics-address-content" ref={rootRef}>
          <div className="logistics-address-tree" style={localStore.getNavBounds}>
            <DragBar
              parentRef={rootRef}
              store={localStore}
            />
            {productDataSet.current &&
            <SkuTree currentCode={currentCode} setCurrentCode={setCurrentCode} dataSet={productDataSet}
                     skuDataSet={skuDataSet}/>}
          </div>
          <div className="logistics-address-right">
            <Form dataSet={productDataSet} columns={2} disabled={true}>
              <TextField name="parentId"/>
              <TextField name="name"/>
              <TextField name="categoryType"/>
              <Select name="status"/>
              <Select name="leafFlag"/>
              <div/>
              <DateTimePicker
                filter={(currentDate, selected) => {
                  if (!skuDataSet.current?.get('createdTime')) return true;
                  else {
                    return moment(currentDate).diff(skuDataSet.current?.get('createdTime'), 'second') < 0;
                  }
                }}
                name="createdTime"
              />
              <DateTimePicker
                filter={(currentDate, selected) => {
                  if (!skuDataSet.current?.get('lastUpdatedTime')) return true;
                  else {
                    return moment(currentDate).diff(skuDataSet.current?.get('lastUpdatedTime'), 'second') > 0;
                  }
                }}
                name="lastUpdatedTime"
              />
            </Form>
            <h3 style={{marginTop: 20}}>SKU库存设置</h3>
            <div>
              <Button color="primary" icon="add" onClick={() => openModal(skuDataSet, 'SKU库存设置', 'create')}>添加</Button>
              <Button color="primary" icon="refresh" onClick={() => skuDataSet.query()}>刷新</Button>
            </div>
            {getTable(skuDataSet, 'SKU库存设置')}
          </div>
        </div>
      </Content>
    </Page>
  );
});
