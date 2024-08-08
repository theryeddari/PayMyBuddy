// app/layouts/AuthLayout.tsx
import React from "react";

interface AuthLayout {
    children?: React.ReactNode;
}


const AuthLayout: React.FC<AuthLayout> = ({children}) => {


    return (
        <div className="h-full w-full flex items-center justify-center  sm:min-h-[900px] ">
            <div
                className="flex flex-col items-center sm:min-h sm:contain-layout justify-evenly w-full h-full sm:h-2/3 sm:w-1/4 bg-white min-w-min rounded-lg shadow-lg overflow-auto sm:overflow-y-hidden">
                <div className="w-full h-full p-16 flex flex-col justify-around">
                    <h1 className="w-full text-2xl font-bold text-center text-nowrap text-white bg-amber-400 p-3 rounded-xl">Pay
                        My Buddy</h1>
                    {children}
                </div>
            </div>
        </div>
    );
};

export default AuthLayout;