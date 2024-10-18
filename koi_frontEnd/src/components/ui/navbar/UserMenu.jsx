import { Popconfirm } from "antd";
import { Modal } from "antd";
import React, { useState } from "react";
import { UserOutlined } from "@ant-design/icons";
import { AiOutlineMenu } from "react-icons/ai";
import { Button, Popover } from "antd";
import { useTranslation } from "react-i18next";
import { NavLink, useNavigate } from "react-router-dom";
import { PATH } from "../../../constant";
import LoginForm from "../auth/LoginForm";
import RegisterForm from "../auth/RegisterForm";
import ForgotPassword from "../auth/ForgotPassword"; // Import ForgotPassword
import { useDispatch, useSelector } from "react-redux";
import { LOCAL_STORAGE_LOGIN_KEY } from "../../../constant/localStorage";
import { manageUserActions } from "../../../store/manageUser/slice";
import { manageCartActions } from "../../../store/manageCart/slice";

export const UserMenu = () => {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation();
  const dispatch = useDispatch();
  const naigate = useNavigate();
  const handleOpenChange = (newOpen) => {
    setOpen(newOpen);
  };

  const handleLogout = () => {
    localStorage.removeItem(LOCAL_STORAGE_LOGIN_KEY);
    dispatch(manageUserActions.updateUserLogin(null));
    dispatch(manageCartActions.setCartCount(null));
    naigate(PATH.HOME);
  };

  const { userLogin } = useSelector((state) => state.manageUser);

  const [isModalLogin, setIsModalLogin] = useState(false);
  const [isModalRegister, setIsModalRegister] = useState(false);
  const [isModalForgotPassword, setIsModalForgotPassword] = useState(false); // State cho Forgot Password

  const showModalLogin = () => {
    setIsModalRegister(false);
    setIsModalForgotPassword(false);
    setIsModalLogin(true);
  };

  const showModalRegister = () => {
    setIsModalLogin(false);
    setIsModalForgotPassword(false);
    setIsModalRegister(true);
  };

  const showModalForgotPassword = () => {
    setIsModalLogin(false);
    setIsModalRegister(false);
    setIsModalForgotPassword(true);
  };

  const handleOkLogin = () => {
    setIsModalLogin(false);
  };

  const handleOkRegister = () => {
    setIsModalRegister(false);
  };

  const handleCancelLogin = () => {
    setIsModalLogin(false);
  };

  const handleCancelRegister = () => {
    setIsModalRegister(false);
  };

  const handleCancelForgotPassword = () => {
    setIsModalForgotPassword(false);
  };

  const renderAvatar = () => {
    return <UserOutlined style={{ color: "white" }} />;
  };

  const renderUser = () => {
    if (!userLogin) {
      return (
        <div className="flex flex-col">
          <Button
            className="mb-[10px] !w-[100px] text-center bg-black text-white hover:!text-white border-none hover:!bg-black"
            onClick={showModalLogin}
          >
            {t("Login")}
          </Button>
          <Button
            className="!w-[100px] text-center bg-white text-black hover:!text-white hover:!border-black hover:!bg-black"
            onClick={showModalRegister}
          >
            {t("Register")}
          </Button>
        </div>
      );
    }

    // If user is logged in
    return userLogin.roles?.map((role) => {
      if (role.name === "ROLE_ADMIN") {
        console.log("a");
        return (
          <div className="flex flex-col" key="admin">
            <NavLink
              to={PATH.MANAGE_USER}
              className="!mb-[15px] rounded-[6px] px-[15px] py-[4px] !w-[100px] text-center bg-black text-white hover:!text-white border-none hover:!bg-black"
            >
              {t("Admin")}
            </NavLink>
            <NavLink
              className=" bg-white rounded-[6px] px-[15px] py-[4px] border-[1px] !w-[100px] text-center mb-[15px] duration-300 transition-all text-black hover:!text-white hover:!border-black hover:!bg-black"
              to={PATH.PROFILE}
            >
              {t("Profile")}
            </NavLink>
            <Button
              onClick={handleLogout}
              className=" bg-white text-black hover:!text-white !w-[100px] text-center hover:!border-black hover:!bg-black"
            >
              {t("Logout")}
            </Button>
          </div>
        );
      }
      if (role.name === "ROLE_MEMBER") {
        return (
          <div className="flex flex-col" key="member">
            <NavLink
              to={PATH.KOI_MANAGEMENT}
              className="rounded-[6px] !w-[100px] text-center px-[15px] py-[4px]  bg-black text-white hover:!text-white border-none hover:!bg-black"
            >
              {t("Manage")}
            </NavLink>
            <NavLink
              className=" bg-white rounded-[6px] px-[15px] py-[4px] border-[1px] !w-[100px] text-center my-[15px] duration-300 transition-all text-black hover:!text-white hover:!border-black hover:!bg-black"
              to={PATH.PROFILE}
            >
              {t("Profile")}
            </NavLink>
            <NavLink
              className="!w-[100px] text-center bg-white rounded-[6px] px-[15px] py-[4px] border-[1px] mb-[15px] duration-300 transition-all text-black hover:!text-white hover:!border-black hover:!bg-black"
              to={PATH.HISTORY_ORDER}
            >
              {t("History")}
            </NavLink>
            <Button
              onClick={handleLogout}
              className="!w-[100px] text-center duration-300 transition-all bg-white text-black hover:!text-white hover:!border-black hover:!bg-black"
            >
              {t("Logout")}
            </Button>
          </div>
        );
      }
      if (role.name === "ROLE_SHOP") {
        return (
          <div className="flex flex-col" key="admin">
            <NavLink
              to={PATH.MANAGE_BLOG}
              className="!mb-[10px] rounded-[6px] px-[15px] py-[4px] !w-[120px] text-center bg-black text-white border-none hover:!text-white hover:!bg-black"
            >
              {t("Manage Blog")}
            </NavLink>
            <NavLink
              to={PATH.PROFILE}
              className="!mb-[10px] rounded-[6px] px-[15px] py-[4px] !w-[120px] text-center bg-white text-black border-[1px] border-black hover:!text-white hover:!bg-black"
            >
              {t("Profile")}
            </NavLink>
            <Button
              onClick={handleLogout}
              className="!mb-[10px] rounded-[6px] px-[15px] py-[4px] !w-[120px] text-center bg-white text-black hover:!text-white border-black hover:!bg-black"
            >
              {t("Logout")}
            </Button>
          </div>
        );
      }
      return null;
    });
  };

  return (
    <div className="relative">
      <div className="flex flex-row items-center gap-1">
        <Popover
          content={renderUser()}
          title=""
          trigger="click"
          open={open}
          onOpenChange={handleOpenChange}
        >
          <div className="p-4 md:py-1 md:px-2 border-[1px] border-neutral-200 flex flex-row items-center gap-3 rounded-full cursor-pointer hover:shadow-md transition">
            <AiOutlineMenu style={{ color: "white" }} />
            <div className="m-1 hidden md:block">{renderAvatar()}</div>
          </div>
        </Popover>
      </div>
      {/* Modal được đưa ra ngoài */}
      <Modal
        title=""
        open={isModalLogin}
        onOk={handleOkLogin}
        onCancel={handleCancelLogin}
      >
        <LoginForm
          showModalRegister={showModalRegister}
          handleOkLogin={handleOkLogin}
          showModalForgotPassword={showModalForgotPassword}
        />
      </Modal>
      <Modal
        title=""
        open={isModalRegister}
        onOk={handleOkRegister}
        onCancel={handleCancelRegister}
      >
        <RegisterForm showModalLogin={showModalLogin} />
      </Modal>
      <Modal
        title=""
        open={isModalForgotPassword}
        onOk={handleCancelForgotPassword}
        onCancel={handleCancelForgotPassword}
      >
        <ForgotPassword showModalLogin={showModalLogin} />
      </Modal>
    </div>
  );
};
