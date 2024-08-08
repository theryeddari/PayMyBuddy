// app/dashboard/relation/page.tsx

import React from "react";
import DashboardLayout from "@/app/dashboard/DashboardLayout";
import {AggregationProvider} from "@/util/Context";
import TransactionTable from "@/components/aggregationTransfer/TransactionTable";
import TransferForm from "@/components/form/TransferForm";

const RelationPage: React.FC = () => {

    return (
        <DashboardLayout>
            <AggregationProvider>
                <div
                    className={"h-full w-full flex flex-col items-center space-y-20 pt-5 pb-20 sm:p-14 overflow-y-auto"}>
                    <TransferForm></TransferForm>
                    <TransactionTable></TransactionTable>
                </div>
            </AggregationProvider>
        </DashboardLayout>
    );
};

export default RelationPage;