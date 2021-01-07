import React, { Fragment, useMemo } from 'react';
import classnames from 'classnames';
import Draggable from 'react-draggable';
import { useResize, X_AXIS_WIDTH, X_AXIS_WIDTH_MAX } from './useResize';

import './index.less';

export default function DragBar({ parentRef, store }) {
  const {
    isDragging,
    bounds,
    resizeNav,
    draggable,
    handleUnsetDrag,
    handleStartDrag,
    handleDrag,
  } = useResize(parentRef, store);

  const draggableClass = useMemo(() => classnames({
    'bim-draggers-handle': true,
    'bim-draggers-handle-dragged': isDragging,
  }), [isDragging]);

  const dragRight = resizeNav.x >= X_AXIS_WIDTH_MAX ? X_AXIS_WIDTH_MAX : bounds.width - X_AXIS_WIDTH;
  return (
    <Fragment>
      {true && (
      <Fragment>
        <Draggable
          axis="x"
          position={resizeNav}
          bounds={{
            left: X_AXIS_WIDTH,
            right: dragRight,
            top: 0,
            bottom: 0,
          }}
          onStart={handleStartDrag}
          onDrag={handleDrag}
          onStop={handleUnsetDrag}
        >
          <div className={draggableClass}>
            <div className="bim-draggers-gun" />
          </div>
        </Draggable>
        {isDragging && <div className="bim-draggers-blocker" />}
      </Fragment>
      )}
    </Fragment>
  );
}
