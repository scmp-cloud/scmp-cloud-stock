import React, {useMemo, useEffect, useRef, useState} from 'react';
import {observer} from 'mobx-react-lite';
import {Upload, SelectBox, Button, Form, message, DataSet, DatePicker} from 'choerodon-ui/pro';
import {Input} from 'choerodon-ui';
import {axios} from '@buildrun/boot';

const {Option} = SelectBox;

function useInterval(callback, delay) {
  const savedCallback = useRef();

  // 保存新回调
  useEffect(() => {
    if (!savedCallback.current) {
      callback();
    }
    savedCallback.current = callback;
  });

  // 建立 interval
  useEffect(() => {
    function tick() {
      savedCallback.current();
    }

    if (delay) {
      // eslint-disable-next-line prefer-const
      let id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}

export default observer((props) => {
  const [excelUrl, setExcelUrl] = useState(false);
  const dataSet = useMemo(() => new DataSet({
    data: [{mode: 'simple'}],
    fields: [
      {name: 'mode', type: 'string'},
    ],
  }), []);

  async function queryExcelUrl() {
    const res = await axios.get('/logistics/v1/dc_settings/url');
    setExcelUrl(res.excelUrl);
  }

  // useInterval(queryExcelUrl, 3000);
  async function handleDownload() {
    let url = '/logistics/v1/dc_settings/export';
    if (dataSet.current?.get('mode') === 'simple' && dataSet.current?.get('date')) {
      url += `?startDate=${dataSet.current?.get('date')?.format('YYYY-MM-DD') || ''}&endDate=${dataSet.current?.get('date')?.format('YYYY-MM-DD') || ''}`
    } else if (dataSet.current?.get('mode') === 'adrSetting') {
      url += `/addressSetting?startDate=${dataSet.current?.get('date')?.format('YYYY-MM-DD') || ''}`
    } else {
      url += `?startDate=${dataSet.current?.get('startDate')?.format('YYYY-MM-DD') || ''}&endDate=${dataSet.current?.get('endDate')?.format('YYYY-MM-DD') || ''}`;
    }
    try {
      const res = await axios.get(url, {timeout: 1000 * 60 * 5, responseType: 'arraybuffer'});
      if (res.failed) {
        message.error(res.message);
      } else {
        debugger;
        const blob = new Blob([res], {type: 'application/vnd.ms-excel'})
        const blobURL = URL.createObjectURL(blob);
        const tempLink = document.createElement('a')
        tempLink.style.display = 'none';
        tempLink.href = blobURL;
        tempLink.setAttribute('download', decodeURI(`导出配置${new Date().getTime()}.xls`));
        // 兼容：某些浏览器不支持HTML5的download属性
        if (typeof tempLink.download === 'undefined') {
          tempLink.setAttribute('target', '_blank')
        }
        // 挂载a标签
        document.body.appendChild(tempLink)
        tempLink.click()
        document.body.removeChild(tempLink)
        // 释放blob URL地址
        window.URL.revokeObjectURL(blobURL)
      }
    } catch (e) {
      message.error(e.message);
    }
  }

  return (
    <div>
      <Form dataSet={dataSet}>
        <SelectBox record={dataSet.current} mode="button" name="mode">
          <Option value="simple">简约模式</Option>
          <Option value="full">完整模式</Option>
          <Option value="adrSetting">地址配置</Option>
        </SelectBox>
        {dataSet.current.get('mode') === 'simple' || dataSet.current.get('mode') === 'adrSetting' ? (
          <DatePicker label="日期" name="date"></DatePicker>
        ) : [
          <DatePicker label="开始日期" name="startDate"></DatePicker>,
          <DatePicker label="结束日期" name="endDate"></DatePicker>
        ]}
      </Form>
      <Button
        onClick={handleDownload}
        color="primary"
        funcType="raised"
        style={{marginBottom: 16}}
      >
        下载导出文件
      </Button>
      <a id="download_excel" href={excelUrl}/>
    </div>
  );
});
