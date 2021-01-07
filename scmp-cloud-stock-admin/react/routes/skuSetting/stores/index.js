import React, {createContext, useMemo} from 'react';
import {inject} from 'mobx-react';
import {injectIntl} from 'react-intl';
import {DataSet} from 'choerodon-ui/pro';
import SkuDataSet from './SkuDataSet';
import useStore from './useStore';
import ProductDataSet from "./ProductDataSet";

const Stores = createContext();

export default Stores;

export const StoreProvider = injectIntl(inject('AppState')(
  (props) => {
    const {
      AppState: {currentMenuType: {id, organizationId}},
      children,
    } = props;

    const booleanDataSet = useMemo(() => new DataSet({
      data: [
        {value: 1, meaning: '是'},
        {value: 0, meaning: '否'},
      ],
    }), []);

    const sourceCodeDataSet = new DataSet({
      pageSize: 23,
      data: [
        {value: 'HC01', meaning: 'HC01/北京朝阳仓'},
        {value: 'HC02', meaning: 'HC02/北京海淀仓'},
        {value: 'HC03', meaning: 'HC03/深圳龙岗仓'},
        {value: 'HC04', meaning: 'HC04/深圳龙华仓'},
        {value: 'HC05', meaning: 'HC05/上海黄浦仓'},
        {value: 'HC06', meaning: 'HC06/上海徐汇仓'},
        {value: 'HC07', meaning: 'HC07/广州天河仓'},
        {value: 'HC08', meaning: 'HC08/广州番禺仓'},
        {value: 'HC09', meaning: 'HC09/广州海珠仓'},
        {value: 'HC10', meaning: 'HC10/广州花都仓'},
        {value: 'HC11', meaning: 'HC11/广州越秀仓'},
        {value: 'HC12', meaning: 'HC12/上海闵行仓'},
        {value: 'HC13', meaning: 'HC13/上海宝山仓'},
        {value: 'HC14', meaning: 'HC14/上海嘉定仓'},
        {value: 'HC15', meaning: 'HC15/苏州沧浪仓'},
        {value: 'HC16', meaning: 'HC16/北京海淀仓'},
        {value: 'HC17', meaning: 'HC17/常州武进仓'},
        {value: 'HC18', meaning: 'HC18/徐州云龙仓'},
        {value: 'HC19', meaning: 'HC19/无锡北塘仓'},
        {value: 'HC20', meaning: 'HC20/南京玄武仓'},
        {value: 'HC21', meaning: 'HC21/南京雨花台仓'},
        {value: 'HC22', meaning: 'HC22/武汉武昌仓'},
        {value: 'HC23', meaning: 'HC23/武汉洪山仓'},
      ],
    });
    const fulfillDataSet = new DataSet({
      pageSize: 23,
      data: [
        {value: 'HC01', meaning: 'HC01/北京朝阳履行仓'},
        {value: 'HC02', meaning: 'HC02/北京海淀履行仓'},
        {value: 'HC03', meaning: 'HC03/深圳龙岗履行仓'},
        {value: 'HC04', meaning: 'HC04/深圳龙华履行仓'},
        {value: 'HC05', meaning: 'HC05/上海黄浦履行仓'},
        {value: 'HC06', meaning: 'HC06/上海徐汇履行仓'},
        {value: 'HC07', meaning: 'HC07/广州天河履行仓'},
        {value: 'HC08', meaning: 'HC08/广州番禺履行仓'},
        {value: 'HC09', meaning: 'HC09/广州海珠履行仓'},
        {value: 'HC10', meaning: 'HC10/广州花都履行仓'},
        {value: 'HC11', meaning: 'HC11/广州越秀履行仓'},
        {value: 'HC12', meaning: 'HC12/上海闵行履行仓'},
        {value: 'HC13', meaning: 'HC13/上海宝山履行仓'},
        {value: 'HC14', meaning: 'HC14/上海嘉定履行仓'},
        {value: 'HC15', meaning: 'HC15/苏州沧浪履行仓'},
        {value: 'HC16', meaning: 'HC16/北京海淀履行仓'},
        {value: 'HC17', meaning: 'HC17/常州武进履行仓'},
        {value: 'HC18', meaning: 'HC18/徐州云龙履行仓'},
        {value: 'HC19', meaning: 'HC19/无锡北塘履行仓'},
        {value: 'HC20', meaning: 'HC20/南京玄武履行仓'},
        {value: 'HC21', meaning: 'HC21/南京雨花台履行仓'},
        {value: 'HC22', meaning: 'HC22/武汉武昌履行仓'},
        {value: 'HC23', meaning: 'HC23/武汉洪山履行仓'},
      ],
    });
    const deliveryTypeDataSet = new DataSet({
      data: [
        {value: '05', meaning: '05/B2C'},
        {value: '06', meaning: '06/B2B'},
      ],
    });

    const stockCodeDataSet = new DataSet({
      data: [
        {value: 'S21', meaning: 'S21/S类逻辑仓'},
        {value: 'T31', meaning: 'T31/T类逻辑仓'},
        {value: 'K41', meaning: 'K41/K类逻辑仓'},
      ],
    });
    const localStore = useStore();
    const skuDataSet = useMemo(() => new DataSet(SkuDataSet()), []);
    const productDataSet = useMemo(() => new DataSet(ProductDataSet({booleanDataSet})), []);

    const value = {
      ...props,
      skuDataSet,
      localStore,
      productDataSet,
      sourceCodeDataSet,
      stockCodeDataSet,
      fulfillDataSet,
      booleanDataSet,
      deliveryTypeDataSet,
      prefixCls: 'stock-address',
    };

    return (
      <Stores.Provider value={value}>
        {children}
      </Stores.Provider>
    );
  },
));
