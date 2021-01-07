export default function ({ type, warehouseOptionDataSet }) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: ({ data: { code } }) => ({
        url: `/logistics/v1/dc_settings/${code}/${type}`,
        paramsSerializer: () => '',
        method: 'get',
      }),
      update: ({ data: [data] }) => ({
        url: `/logistics/v1/dc_settings/${data.code}/${type}`,
        method: 'post',
        data: {
          ...data,
          id: undefined,
        },
      }),
      create: ({ data: [data] }) => ({
        url: `/logistics/v1/dc_settings/${data.code}/${type}`,
        method: 'post',
        data,
      }),
    },
    queryParameter: {
      code: '1',
    },
    fields: [
      { name: 'name', label: '商品类目/名称', required: true },
      { name: 'categoryType', label: '类型级别', required: true },
      { name: 'warehouseLevel1', label: '一级提货仓', options: warehouseOptionDataSet, required: true },
      { name: 'warehouseLevel2', label: '二级提货仓', options: warehouseOptionDataSet },
      { name: 'warehouseLevel3', label: '三级提货仓', options: warehouseOptionDataSet },
      { name: 'startUpCost1', label: '一级服务费' },
      { name: 'startUpCost2', label: '二级服务费' },
      { name: 'startUpCost3', label: '三级服务费' },
      { name: 'remark', label: '备注' },
      { name: 'addressCode', label: '配置来源' },
      { name: 'status', label: '状态' },
      { name: 'effectiveDate', label: '生效时间', type: 'datetime', required: true },
      { name: 'inactiveDate', label: '失效时间', type: 'datetime', required: true },
    ],

  };
}
