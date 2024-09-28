import React, { useState, useEffect } from "react";
import { useGetOrder } from "../../../hooks/order/useGetOrder";
import { useSelector } from "react-redux";
import { Radio, Tabs } from "antd";
import OrderPaid from "../order/OrderPaid";
import OrderPending from "../order/OrderPending";
import OrderCancel from "../order/OrderCancel";

const HistoryOrder = () => {
  const userLogin = useSelector((state) => state.manageUser.userLogin);
  const { data: lstOrder , refetch, isFetching} = useGetOrder(userLogin?.id);
  const [mode, setMode] = useState("top");

  const [lstPaid, setLstPaid] = useState([]);
  const [lstPending, setLstPending] = useState([]);
  const [lstCancel, setLstCancel] = useState([]);

  // Update logic to avoid multiple re-renders
  useEffect(() => {
    const paidOrders =
      lstOrder?.filter((order) => order?.status === "PAID") || [];
    const pendingOrders =
      lstOrder?.filter((order) => order?.status === "PENDING") || [];
    const cancelledOrders =
      lstOrder?.filter((order) => order?.status === "CANCELLED") || [];

    setLstPaid(paidOrders);
    setLstPending(pendingOrders);
    setLstCancel(cancelledOrders);
  }, [lstOrder]); // Dependency on lstOrder

  const handleModeChange = (e) => {
    setMode(e.target.value);
  };

  const [activeKey, setActiveKey] = useState("paid"); // Add state for active tab

  const switchToCancelledTab = () => {
    setActiveKey("cancel"); // Set active key to 'cancel'
  };

  return (
    <div className="my-[60px]">
      <h2 className="font-bold text-[24px] mb-[40px]">History Order</h2>
      <div>
        <Radio.Group
          onChange={handleModeChange}
          value={mode}
          style={{
            marginBottom: 8,
          }}
        >
          <Radio.Button value="top">Horizontal</Radio.Button>
          <Radio.Button value="left">Vertical</Radio.Button>
        </Radio.Group>
        <Tabs
          activeKey={activeKey} // Use activeKey state
          onChange={setActiveKey} // Update activeKey on tab change
          defaultActiveKey="1"
          tabPosition={mode}
          style={{}}
          items={[
            {
              label: "Paid Orders",
              key: "paid",
              children: <OrderPaid lstPaid={lstPaid} isFetching={isFetching} />,
            },
            {
              label: "Pending Orders",
              key: "pending",
              children: <OrderPending lstPending={lstPending} isFetching={isFetching} refetch={refetch} switchToCancelledTab={switchToCancelledTab} />,
            },
            {
              label: "Cancelled Orders",
              key: "cancel",
              children: <OrderCancel lstCancel={lstCancel} isFetching={isFetching} />,
            },
          ]}
        />
      </div>
    </div>
  );
};

export default HistoryOrder;
