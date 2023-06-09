import { createStore,applyMiddleware,compose } from "redux";
import thunk from "redux-thunk";
import authReducer from "./authReducer";
import SecureLS from 'secure-ls';
import { setAuthorizationHeader } from "../api/apiCalls";


const secureLs=new SecureLS()

const getStateFromStorage = () => {
  const hoaxAuth = secureLs.get("hoax-auth");

  let stateInLocalStorage = {
    isLoggedIn: false,
    username: undefined,
    displayName: undefined,
    image: undefined,
    password: undefined,
  };

  if (hoaxAuth) {
      return hoaxAuth;
  }
  return stateInLocalStorage;
};






const updateStateInStorage=(newState)=>{
  secureLs.set("hoax-auth",newState)
}

// window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const configureStore = () => {
  const initialState = getStateFromStorage();
  setAuthorizationHeader(initialState);
  const store = createStore(authReducer, initialState, composeEnhancers(applyMiddleware(thunk)));

  store.subscribe(() => {
    updateStateInStorage(store.getState());
    setAuthorizationHeader(store.getState());

  });

  return store;
};

export default configureStore;
