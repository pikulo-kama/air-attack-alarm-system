import React from "react";

const ApplicationDropdown = React.forwardRef(
    ({children, style, className, 'aria-labelledby': labeledBy}, ref) => {
        return (
            <div
                ref={ref}
                style={style}
                className={className}
                aria-labelledby={labeledBy}
            >
                <ul className="list-unstyled" style={{height: "200px", overflowY: "auto"}}>
                    {children}
                </ul>
            </div>
        );
    },
);

export default ApplicationDropdown