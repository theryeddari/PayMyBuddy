"use client"
import React, {useState} from "react";
import Header from "@/components/Header";
import LogoutScript from "@/components/form/LogoutScript";
import Link from "next/link";
import {useRouter} from "next/navigation";

interface DashboardLayout {
    children?: React.ReactNode;
}

const DashboardLayout: React.FC<DashboardLayout> = ({children}) => {
    const router = useRouter();
    const [active, setActive] = useState<boolean>(false);


    //vérifie la présence d'un jwtToken pour s'assurer que la personne était connecté. (ne vérife pas l'authenticité etc)
    const jwtToken = localStorage.getItem("jwtToken");
    if (!jwtToken || jwtToken.startsWith("Bearer ")) {
        router.push('/auth/login');
    }

    const handleRedirection = async (event: React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault();
        setActive(true);
    }

    return (
        <div className={"h-full w-full flex flex-col items-center"}>
            <Header nameApplication={"Pay my Buddy"}>
                <Link href={"/dashboard/transfers"}>Transférer</Link>
                <Link href={"/dashboard/profile"}>Profile</Link>
                <Link href={"/dashboard/relation"}>Ajouter relation</Link>
                <a href="#" onClick={handleRedirection}>Se déconnecter</a>
            </Header>
            <div className={" h-full w-full flex flex-col justify-center items-center"}>
                {active && <LogoutScript/>}
                {children}
            </div>
        </div>
    );
};

export default DashboardLayout;