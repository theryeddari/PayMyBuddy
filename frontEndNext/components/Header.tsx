"use client";

import React from "react";
import {usePathname} from "next/navigation";
import Link from "next/link";

interface Header {
    nameApplication: string;
    children: React.ReactNode; // Peut contenir des éléments <Link>
}

const Header: React.FC<Header> = ({nameApplication, children}) => {
    const currentRoute = usePathname();
    const links: React.ReactElement[] = [];

    const extractAndModifyLinks = (children: React.ReactNode) => {

        React.Children.toArray(children).forEach(child => {
            if (React.isValidElement(child)) {
                const element = child as React.ReactElement;

                // Vérifiez si l'élément est un <Link> ou un <a>
                if (element.type === Link || element.type === 'a') {
                    const href = (element.props.href) as string; // Obtenez le href
                    const isActive = href === currentRoute;
                    const className = isActive
                        ? 'hover:text-blue-600 underline underline-offset-8'
                        : 'hover:text-blue-600';

                    // Cloner l'élément Link ou <a> et ajouter/modifier la classe
                    const linkModded = React.cloneElement(element, {
                        className: `${className}`.trim()
                    });
                    console.log(linkModded)
                    links.push(linkModded);
                }
            }
        });
        console.log(links)

        return links as React.DetailedReactHTMLElement<any, any>;

    };

    extractAndModifyLinks(children);

    return (
        <header id="topHeader"
                className="flex items-center justify-center w-full min-h-min min-w-min sm:h-1/6 pb-3 pr-3 sm:p-20 bg-opacity-30">
            <div id="applicationNameHeader" className="col-start-1 text-xl font-bold ml-3 ">
                {nameApplication}
            </div>
            <nav id="menuHeader" className="flex-1 flex justify-end flex-wrap text-x space-x-10 font-bold ">
                {links}
            </nav>
        </header>
    );
};

export default Header;