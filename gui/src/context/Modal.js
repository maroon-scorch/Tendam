import React, { useCEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import './Modal.css';

const modalSrc = document.getElementById('modal-wrapper');

function Modal({children, isOpen = false}) {
    const [show, setShow] = useState(isOpen);
    // const [content, setContent] = useState();

    function close() {
        setShow(false);
    }

    function open() {
        setShow(true);
    }

    return createPortal(
        <div id="modal-wrapper" style={{display: show ? 'block' : 'none' }}>
        <div id="modal-overlay" onClick={close}></div>
        <div id="modal-package">
            <div className="modal-header">RESULT</div>
            <div className="modal-exit" onClick={close}>X</div>
            <div className="modal-body">
                {children}
            </div>
        </div>
        </div>, modalSrc);
}

export default Modal
