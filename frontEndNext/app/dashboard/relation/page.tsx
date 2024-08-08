import DashboardLayout from "@/app/dashboard/DashboardLayout";
import AddRelationForm from "@/components/form/AddRelationForm";
import React from "react";


const RelationPage: React.FC = () => {
    return (
        <DashboardLayout>
            <AddRelationForm></AddRelationForm>
        </DashboardLayout>
    );
};

export default RelationPage;