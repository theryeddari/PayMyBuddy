// app/dashboard/relation/page.tsx
import React from "react";
import DashboardLayout from "@/app/dashboard/DashboardLayout";
import Profile from "@/components/form/ProfileForm";

const RelationPage: React.FC = () => {
    return (
        <DashboardLayout>
            <Profile></Profile>
        </DashboardLayout>
    );
};

export default RelationPage;