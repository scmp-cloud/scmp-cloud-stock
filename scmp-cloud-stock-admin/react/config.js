const config = {
  projectType: 'choerodon',
  buildType: 'single',
  master: './node_modules/@buildrun/br-e-master/lib/master.js',
  dashboard: {},
  modules: [
    '.',
  ],

  port: 9090,
  resourcesLevel: ['site', 'user'],
};

module.exports = config;
