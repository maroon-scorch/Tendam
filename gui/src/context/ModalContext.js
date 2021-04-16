import React, { useContext, useState } from 'react';
import './Modal.css';

const ModalContext = React.createContext();
const ModalContextProvider = ModalContext.Provider;

export function useModal() {
    return useContext(ModalContext);
};

export function ModalProvider({ children }) {

    const [show, setShow] = useState(false);
    const [content, setContent] = useState();

    function close() {
        setShow(false);
    }

    function open() {
        setShow(true);
    }

    function Modal() {
        return (
            <div id="modal-wrapper" style={{display: show ? 'block' : 'none' }}>
                <div id="modal-overlay" onClick={close}></div>
                <div id="modal-package">
                    <div className="modal-header">RESULT</div>
                    <div className="modal-exit" onClick={close}>X</div>
                    <div className="modal-body">
                        {content}
                    </div>
                </div>
            </div>
        )
    }

    
    const modalInfo = {
        close,
        open,
        setContent,
        Modal
    };

    return (
        <ModalContextProvider value={modalInfo}>
            <Modal />
            {children}
        </ModalContextProvider>
    );
};