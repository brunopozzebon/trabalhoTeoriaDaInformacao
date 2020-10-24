import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { HashRouter } from "react-router-dom";
import { Routes } from "./routes/routes";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import * as serviceWorker from "./serviceWorker";
import {
  ProcessingProvider,
  CodificationMethodProvider,
  FinishedCodificationProvider,
  CodewordsProvider,
  IndexProvider,
  GoulombDivisorProvider
} from "./context";


ReactDOM.render(
  <React.StrictMode>
    <FinishedCodificationProvider>
      <CodificationMethodProvider>
        <ProcessingProvider>
          <CodewordsProvider>
            <IndexProvider>
              <GoulombDivisorProvider>
                <HashRouter>
                  <Routes />
                  <ToastContainer />
                </HashRouter>
              </GoulombDivisorProvider>             
            </IndexProvider>
          </CodewordsProvider>
        </ProcessingProvider>
      </CodificationMethodProvider>
    </FinishedCodificationProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

serviceWorker.unregister();