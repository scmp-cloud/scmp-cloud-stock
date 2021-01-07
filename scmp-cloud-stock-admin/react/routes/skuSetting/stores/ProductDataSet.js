export default function ({booleanDataSet}) {
  return {
    autoQuery: true,
    selection: false,
    paging: false,
    transport: {
      read: {
        url: '/stock/v1/goods',
        method: 'get',
      },
      update: ({data: [data]}) => ({
        url: `/logistics/v1/addresses/${data.code}`,
        method: 'put',
        data,
      }),
    },
    fields: [
      {name: 'id', label: '商品id'},
      {name: 'parentId', label: '父级id', readOnly: true},
      {name: 'name', label: '商品类型/名称', readOnly: true},
      {name: 'categoryType', label: '类型级别', readOnly: true},
      {name: 'status', label: '是否上架', options: booleanDataSet},
      {name: 'leafFlag', label: '是否叶子节点', options: booleanDataSet, readOnly: true},
      {name: 'createdTime', label: '创建时间', type: 'datetime'},
      {name: 'lastUpdatedTime', label: '更新时间', type: 'datetime'},
    ],
    queryParameter: {
      parent_id: '0',
    },

  };
}
