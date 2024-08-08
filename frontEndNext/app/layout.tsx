import type {Metadata} from "next";
import "./globals.css";

export const metadata: Metadata = {
    title: "PayMyBuddy",
    description: "Application de transfert d'argent entre amis",
};

interface RootLayout {
    children: React.ReactNode;
}

export default function RootLayout({children}: Readonly<RootLayout>) {

    return (
        <html lang="fr" className="h-full w-full fixed">
        <body
            className=" h-full w-full font-serif bg-gradient-to-b from-transparent to-gray-300 sm:overflow-y-auto contain-content">{children}</body>
        </html>
    );
}
