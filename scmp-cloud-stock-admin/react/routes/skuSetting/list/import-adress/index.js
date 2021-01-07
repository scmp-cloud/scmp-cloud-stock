import React, { useState } from 'react';
import { observer } from 'mobx-react-lite';
import { Button, Progress, message, Modal, Icon } from 'choerodon-ui/pro';
import { Upload } from 'choerodon-ui';
import { axios, Choerodon } from '@buildrun/boot';
import './index.less';

export default observer(({ context }) => {
  const [completed, setCompleted] = useState(false);
  const { addressDataSet, storeSettingDataSet, homeSettingDataSet } = context;
  async function refresh() {
    // setCurrentCode(addressDataSet.current?.get('code'));
    await addressDataSet.loadData([]);
    await addressDataSet.query();
    storeSettingDataSet.query();
    homeSettingDataSet.query();
  }
  const uploadProps = {
    headers: {
      'Access-Control-Allow-Origin': '*',
      Authorization: `${Choerodon.getAccessToken()}`,
    },
    // eslint-disable-next-line no-underscore-dangle
    action: `${window._env_.API_HOST}/logistics/v1/addresses/import`,
    multiple: true,
    name: 'multipartFile',
    accept: ['.xls', '.xlsx'],
    uploadImmediately: true,
    showUploadList: false,
    onProgress({ percent }) {
      console.log(percent);
      setCompleted(Math.floor(percent > 99 ? 99 : percent));
    },
    onError(e) {
      setCompleted(false);
      message.error(`上传失败${e.message}`);
      refresh();
    },
    onSuccess(response) {
      if (response.failed) {
        message.error(response.message);
        refresh();
      } else {
        setCompleted(100);
        message.info('导入成功');
        refresh();
      }
    },
    beforeUpload: (file) => new Promise((resolve, reject) => {
      Modal.open({
        title: '上传',
        children: `确认上传"${file.name}"并导入吗？`,
        onOk: () => {
          resolve('ok');
        },
        onCancel: () => {
          reject();
        },
      });
    }),
  };
  return (
    <div className="logistics-import">
      <div className="logistics-import-download">
        <h3>下载模板</h3>
        <p>必须使用模版文件，导入地址信息</p>
        <div>
          <Button
            icon="get_app"
            color="primary"
            onClick={async () => {
              const excelData = await axios({
                method: 'get',
                url: '/logistics/v1/templates/addresses',
                responseType: 'blob',
              });
              const aLink = document.getElementById('download');
              const blob = new Blob([excelData], { type: excelData.type });
              const objectUrl = window.URL.createObjectURL(blob);
              aLink.href = objectUrl;
              aLink.download = '地址导入模板.xls';
              aLink.click();
            }}
          >下载模板
          </Button>
        </div>
      </div>
      <a id="download" />
      <h3>导入地址</h3>
      <Upload {...uploadProps}>
        <Button>
          <Icon type="file_upload" /> 选择文件
        </Button>
      </Upload>
      {completed && (
        <div>
          <Progress value={completed} />
          {completed !== 100 && <p>文件正在上传中，请勿关闭页面。</p>}
        </div>
      )}
    </div>
  );
});
