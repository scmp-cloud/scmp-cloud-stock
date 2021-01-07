import React, {useMemo} from 'react';
import {Route, Switch, withRouter} from 'react-router-dom';
// import { inject } from 'mobx-react';
import {asyncRouter, nomatch} from '@buildrun/boot';
import {asyncLocaleProvider} from '@buildrun/utils';

const skuSetting = asyncRouter(() => import('./routes/skuSetting'));

export default withRouter((props) => {
    const {AppState, match} = props;
    const IntlProviderAsync = useMemo(() => asyncLocaleProvider('zh_CN', () => import(`./locale/zh_CN`)), []);
    return (
        <IntlProviderAsync>
            <Switch>
                <Route path={`${match.url}/sku-setting`} component={skuSetting}/>
                <Route path="*" component={nomatch}/>
            </Switch>
        </IntlProviderAsync>
    );
});
