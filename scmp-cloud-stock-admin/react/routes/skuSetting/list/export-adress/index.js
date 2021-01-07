/* eslint-disable jsx-a11y/control-has-associated-label */
import React, { useContext, useEffect, useRef, useState } from 'react';
import { observer } from 'mobx-react-lite';
import { Button, Form, message } from 'choerodon-ui/pro';
import { Input } from 'choerodon-ui';
import { axios } from '@buildrun/boot';

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

export default observer(() => {
  const [excelUrl, setExcelUrl] = useState(false);
  async function queryExcelUrl() {
    const res = await axios.get('/logistics/v1/addresses/url');
    setExcelUrl(res.excelUrl);
  }
  // useInterval(queryExcelUrl, 3000);
  return (
    <div>
      <Button
        onClick={async () => {
          try {
            const res = await axios.get('/logistics/v1/addresses/export');
            if (res.failed) {
              message.error(res.message);
            } else {
              setExcelUrl(res.excelUrl);
              setTimeout(() => {
                document.getElementById('download_excel').click();
              }, 500);
            }
          } catch (e) {
            message.error(e.message);
          }
        }}
        color="primary"
        funcType="raised"
        style={{ marginBottom: 16 }}
      >
        下载导出文件
      </Button>
      <a id="download_excel" href={excelUrl} />
    </div>
  );
});
