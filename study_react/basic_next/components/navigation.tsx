"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { useState } from "react";

export default function Navigation() {
    const path = usePathname();
    const [count, setCount] = useState(0);
    // console.log("hello");
    let show = true;
    if (path === "/about-us") {
        // show = false;
        show = true;
    }

    return (
        <nav style={{display: show ? "block" : "none"}}>
            <ul>
                <li>
                    <Link href="/">Home</Link> {path === "/" ? "🎄" : null}
                </li>
                <li>
                    <Link href="/about-us">About Us</Link> {path === "/about-us" ? "🎄" : null}
                </li>
                <li>
                    <Link href="/about-us/company/jobs/sales">Sales job</Link> {path === "/about-us/company/jobs/sales" ? "🎄" : null}
                </li>
                <button onClick={() => setCount((current) => current + 1)}>{count}</button>
            </ul>
        </nav>
    )
}